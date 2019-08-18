ObjectiveFunction = @parameter_tuning;
x0 = zeros(1, 3);   % starting point
lb = [0.015 0.08 0.005];% boundaries
ub = [0.9 0.9 0.5];
options = optimoptions(@simulannealbnd,'MaxTime', 1800);
[x, fval, exitFlag, output] = simulannealbnd(ObjectiveFunction, x0, lb, ub, options);
save(strcat(cd, '/simulation/output/simulanneal.mat'), 'x');

path = strcat(cd, '/simulation/format.csv');
delete(path);
result = table(x);
writetable(result, path, 'delimiter', ',');
result = readtable(path);
result.Properties.VariableNames = {'wsee','fsee','esee'};
path = strcat(cd, '/simulation/data/', filename, '_tuned.csv');
writetable(result, path, 'delimiter', ',');