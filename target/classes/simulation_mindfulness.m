format long
N=23;

W=[
%    X1     X2     X3     X4    X5    X6      X7    X8    X9   X10   X11   X12   X13   X14   X15   X16   X17    X18     X19    X20    X21    X22     X23
%   wsee   ssee   wsc    ssc  srsee   srsc   fsee  psee  esee  goal  sws   ssb  srsb   esb   meme  ins   psy    memw    ssmy   srsmy  esmy  memlt    bsp
0	     1     0	  0	    0      0      0	    0	  0	    0     0  	0	  0	    0	  0     0 	  0	     0	     0   	0      0      0       0	         % wsee    X1
0        0     0	  0     1	   0	  0	    0	  0	    0     0	    0     0	    0	  0	    1	  0	     0	     0	    0      0      0       0	         % ssee    X2
0	     0     1	  1     0	   0	  0	    0	  0	    0     0	    0	  0  	0	  0     0	  0	     0	     0	    0      0      0       0  	     % wsc     X3
0	     0	   0	  0	    0	   1	  0	    0	  0	    0     0	    0	  0	    0	  0	    0	  0	     0	     0	    0      0      0       0	         % ssc     X4
0	     0	   0	  0	    0	   0	  1     0	  0	    1     0 	0	  0	    0	  1     0	  0	     1       0	    0      0      0       0 	     % srsee   X5
0	     0	   0	  0	    0	   0      0     1	  0	    0     0	    0     0	    0	  0	    0     0	     0	     0	    0      0      0       0	         % srsc    X6
0	     0	   0	  0     0      0	  0	    1	  0	    0     0 	0     0	    0     0	    0     0	     0	     0	    0	   0      0     -0.1         % fsee    X7
0	     0	   0	  0	    1	   0	  0	    0	  1     0     0	    0	  0	    0	  0	    0	  0	     0	     0      0      0      0       0	         % psee    X8
1        0	   0	  0	    0	   0	  0	    0     0	    0     0	    0	  0	    0	  0	    0	  0   	 0       0	    0      0      0       0	         % esee    X9
0	     0	   0	  0	    0	   0	  0	    0	  0	    0     0	    0	  0	    0	  0     0	  1      0	     0	    0      0      0       0	         % goal    X10
0	     0	   0	  0	    0      0    -0.9	0	  0   	0     0	    0	  0	    0	  0     0	  0	     0	     0    	0      0      0       0	         % sws     X11
0	     0	   0	  0	    0      0	  0	    0	  0	    0     0	    0	  1     0	  0     1	  0      0	     0	    0      0      0       0          % ssb     X12
0	     0	   0	  0	    0      0	  0	    0	  0	    0    0.6	0	  0	    0	  0     0	 0.8     0	     0	    0      0      0       0 	     % srsb    X13
0	     0	   0	  0	    0      0	  0	    0     0	    0     0	    1     0	    0	  1     0	  0	     0	     0    	0      0      0       1          % esb     X14
0	     0	   0	  0	    0      0	  0	    0	  0	    0     0	    0	  0 	0	  0     0	  0      1       0    	0      0      1       1          % meme    X15
0	     0	   0	  0	    0      0	-0.6	0     0	    0     1     0	  0	    0	  1     0	  0	     1       0     	0      0      0       1          % ins     X16
0	     0	   0	  0	    0      0	-0.6    0	  0	    0     0	    0	  0	    1	  0     0	  0  	 0	     0      0      1      0       0	         % psy     X17
0	     0	   0	  0     0      0	  0 	0	  0	    0     1     0	  0  	0	  1     0	  1      0	     0      0      0     0.6      0  	     % memw    X18
0	     0	   0	  0	    0      0	  0	    0     0	    0     0	    0	  0	    0	  0     1	  0	     0	     0      1      0      0       0	         % ssmy    X19
0	     0	   0	  0	    0      0	  0     0	  0 	0    0.6    0	  0	    0	  0     0	 0.8     0	     0   	0      0      0       0 	     % srsmy   X20
0	     0	   0	  0	    0      0	  0	    0	  0	    0     0	    0	  0	    0	  1     0	  0  	 0	     1  	0      0      0       1          % esmy    X21
0      0	   0	  0	    0	   0      0	    0	  0	    0	  0     0	  0  	0	  1     0     0	     1   	 0	    0      0      0       1  	     % memlt   X22
0      0	   0	  0	  -0.8	   0      0	    0     0	    0	  0     0	  0  	0	  0	    0    0.5     0  	 0	    0      0      0       0          % bsp     X23
];

%       X1     X2    X3     X4     X5     X6     X7    X8      X9     X10     X11     X12     X13   X14      X15      X16      X17     X18     X19    X20    X21    X22     X23
Sp_f=[0.015  0.015  0.04   0.009  0.015  0.009   0.9  0.015   0.015  0.002    0.03    0.07   0.2    0.05    0.001    0.0004   0.0004  0.001    0.1    0.2    0.01    0.04    0.9];

%    X1     X2    X3     X4     X5     X6     X7    X8    X9   X10   X11   X12   X13   X14   X15   X16   X17    X18     X19    X20    X21    X22     X23
%    wsee   ssee   wsc    ssc  srsee   srsc   fsee  psee  esee  goal  sws   ssb  srsb   esb   meme   ins   psy  memw    ssmy   srsmy   esmy  memlt    bsp
O=[
1	     1	   0	  1     0	   1	  0	    0	  1	    0     0	    1	  1	    1	  0	    0	  0	     0	     1	    1      1      0       0	          % identity
0	     0	   0	  0  	0	   0	  0	    0	  0	    0     0   	0	  0	    0	  0     0	  0	     0 	     0	    0      0      0       0	          % Sum func
0	     0	   0	  0	    1	   0	  1	    1	  0	    0     1	    0	  0	    0	  1	    1	  1	     1	     0	    0      0      1       1	          % scaled sum func
0	     0	   0	  0     2	   0	  1     2	  0     0     4     0	  0	    0	  6	    3	  5      4	     0	    0      0      2       5	          % scaling factor
0	     0 	   0	  0	    0	   0	  0	    0	  0	    0     0	    0	  0	    0	  0	    0	  0	     0	     0	    0      0      0       0	          % normalised sum norm adnorsum
0	     0	   0	  0	    0	   0	  0	    0	  0	    0     0	    0	  0  	0	  0	    0	  0	     0	     0	    0      0      0       0  	      % normalizing factor
0	     0	   0	  0	    0	   0	  0	    0	  0	    0     0	    0	  0	    0	  0	    0	  0	     0	     0	    0      0      0       0	          % adaptive normalised sum adnorsum
0        0	   0	  0	    0	   0	  0	    0	  0	    0     0	    0	  0  	0	  0	    0	  0	     0	     0	    0      0      0       0           % simple logistic  slogistic
0	     0	   0	  0	    0	   0	  0	    0	  0	    0     0  	0	  0	    0	  0	    0	  0	     0	     0	    0      0      0       0	          % advanced logistic alogistic
0	     0	   0	  0	    0	   0	  0	    0	  0   	0     0     0	  0     0	  0	    0	  0	     0	     0	    0      0      0       0           % steepness
0	     0	   0	  0	    0	   0	  0	    0	  0	    0     0	    0	  0  	0	  0	    0	  0	     0	     0	    0      0      0       0  	      % threshold
0	     0	   1	  0	    0	   0	  0	    0	  0	    1     0  	0	  0  	0	  0	    0	  0	     0	     0	    0      0      0       0  	      % adaptive advanced logistic adalogistic
0	     0	  100	  0	    0	   0	  0	    0	  0	 100000   0     0	  0     0     0	    0	  0	     0	     0	    0      0      0       0 	      % steepness
0	     0	  0.9	  0	    0	   0	  0	    0	  0	   0.65   0     0	  0     0     0 	0	  0 	 0	     0	    0      0      0       0 	      % threshold factor
];


id=zeros(1,N);
sum=zeros(1,N);
ssum=zeros(1,N);
lambda=zeros(1,N);
norsum=zeros(1,N);
norlambda=zeros(1,N);
adnorsum=zeros(1,N);
slog=zeros(1,N);
alog=zeros(1,N);
s=zeros(1,N);
t=zeros(1,N);
adalog=zeros(1,N);
adas=zeros(1,N);
adat=zeros(1,N);

id=O(1,:);
sum=O(2,:);
ssum=O(3,:);
lambda=O(4,:);
norsum=O(5,:);
norlambda=O(6,:);
adnorsum=O(7,:);
slog=O(8,:);
alog=O(9,:);
s=O(10,:);
t=O(11,:);
adalog=O(12,:);
adas=O(13,:);
adat=O(14,:);

eta=zeros(N);
hebb=zeros(N);
mu=zeros(N);
slhomo=zeros(N);
alhomo=zeros(N);
sqhomo=zeros(N);
aqhomo=zeros(N);
htau=zeros(N);
amp=zeros(N);
adcon=zeros(14+N,N^2);

% values for the adaptivity connections
eta(13,23)=0.5;
hebb(13,23)=0.7;
mu(13,23)=0.98;

eta(20,23)=0.5;
hebb(20,23)=0.7;
mu(20,23)=0.98;

%user parameters
wsc = 0.95; % .95-1
bsp = 0.6; %.05-.6
% esee = 0.9;

dt=1;
time=0:dt:7000;
L=length(time);
STDX=zeros(L,N);

% initial values
STDX(1,3)=wsc;% ws c
STDX(1,10)=0.01;% goal
STDX(1,15)=bsp;% mem e
STDX(1,18)=bsp;% mem w
STDX(1,23)=bsp;% bs p
% STDX(1,9)=esee;% es ee

k=0;
for i=1:N
    for j=1:N
        k=k+1;
        condy(1,k)=W(i,j);
    end
end


for i=2:L
    
    for j=1:N
        k=0;
        
        for ii=1:N

            for jj=1:N
                k=k+1;
                CC=0;

                for iii=15:14+N
                    jjj=iii-14;
                    CC=CC+adcon(iii,k)*STDX(i-1,jjj);
                end

                C2=1-condy(i-1,k);
                C1=condy(i-1,k);
                C3=htau(ii,jj)-abs(STDX(i-1,ii)-STDX(i-1,jj));
                C4=abs(C3);
                C5=htau(ii,jj)^2-abs(STDX(i-1,ii)-STDX(i-1,jj))^2;
                C6=abs(C5);
                condy(i,k)=condy(i-1,k)+eta(ii,jj)*(hebb(ii,jj)*(...
                    STDX(i-1,ii)*STDX(i-1,jj)*C2+mu(ii,jj)*C1)+...
                        adcon(13,k)*(C1+adcon(14,k)*(CC)*(C2)*...
                            C1)+slhomo(ii,jj)*(C1+amp(ii,jj)*C1*C2*...
                                C3)+alhomo(ii,jj)*(C1+amp(ii,jj)*C2*((C4+C3)/2)+C1*...
                                    ((C4-C3)/2))+sqhomo(ii,jj)*(C1+amp(ii,jj)*C1*C2*C5)+...
                                        aqhomo(ii,jj)*(C1*amp(ii,jj)*C2*((C6+C5)/2)+C1*((C6-C5)/2))-...
                                            C1)*dt;
            end

            C7=0;

            for jj=1:N
                C7=C7+condy(i-1,(jj-1)*N+j)*STDX(i-1,jj);
            end

            wxsum(i-1,j)=C7;
            C11=0;

            for jj=1:N
                C11=C11+condy(i-1,(jj-1)*N+j);
            end

            landa(i-1,j)=C11;

            if ssum(j)>0
                C8=ssum(j)*C7/lambda(j);
            else
                C8=0;
            end

            if norsum(j)>0
                C9=norsum(j)*C7/norlambda;
            else
                C9=0;
            end

            if adnorsum(j)>0
                C10=adnorsum(j)*C7/C11;
            else
                C10=0;
            end

            C12=slog(j)*(1/(1+exp(-s(j)*(C7-t(j)))));
            C13=alog(j)*(((1/(1+exp(-s(j)*(C7-t(j)))))-...
                (1/(1+exp(s(j)*t(j)))))*(1+exp(-s(j)*t(j))));
            C14=adalog(j)*(((1/(1+exp(-adas(j)*(C7-adat(j)*C11))))-...
                (1/(1+exp(adas(j)*adat(j)))))*(1+exp(-adas(j)*adat(j))));
            aggimpact(i-1,j)=id(j)*C7+sum(j)*C7+C8+C9+C10+C12+C13+C14;

            STDX(i,j)=STDX(i-1,j)+Sp_f(j)*(aggimpact(i-1,j)-STDX(i-1,j))*dt;
        end
    end
end

L1 = 'wsee';
L2 = 'ssee';
L3 = 'wsc';
L4 = 'ssc';
L5 = 'srsee';
L6 = 'srsc';
L7 = 'fsee';
L8 = 'psee';
L9 = 'esee';
L10 = 'goal';
L11 = 'sws';
L12 = 'ssb';
L13 = 'srsb';
L14 = 'esb';
L15 = 'meme';
L16 = 'ins';
L17 = 'psy';
L18 = 'memw';
L19 = 'ssmy';
L20 = 'srsmy';
L21 = 'esmy';
L22 = 'memlt';
L23 = 'bsp';

varNames = {L1,L2,L3,L4,L5,L6,L7,L8,L9,L10,L11,L12,L13,L14,L15,L16,L17,L18,L19,L20,L21,L22,L23};

color_array = rand(N, 3);
figure();
for i = 1:N
    plot(time, STDX(:,i), 'color', color_array(i,:),'lineWidth',3)
    hold on
end

legend(varNames)
grid on

% result = table(STDX);
% writetable(result, 'format.csv', 'delimiter', ',', 'precision', 6);
% result = readtable('format.csv');
% result.Properties.VariableNames = varNames;
% writetable(result, 'result.csv', 'delimiter', ',');