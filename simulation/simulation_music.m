format long
N=32;

%    X1     X2     X3     X4    X5    X6      X7    X8    X9   X10   X11   X12   X13   X14   X15   X16    X17    X18    X19    X20    X21    X22     X23   X24   X25   X26   X27    X28     X29    X30    X31    X32 
%   wsee   ssee   wsc    ssc  srsee   srsc   fsee  psee  esee  srsb goalb  psb   wsm1  wsm2  ssm1  ssm2  ssin1  ssin2  srsin1 srsin2 srsm1  srse1   srsm2 srse2  bsn   bsp  psin1  psin2    pse1   pse2  esin1  esin2 
W=[   0	     1	   0	  0	    0      0      0	    0	  0	    0     0  	0	  0	    0	  0     0 	  0	     0	     0   	0      0      0       0	    0	  0     0 	  0	     0	     0   	0      0      0        % wsee    X1  
      0      0     0	  0     1	   0	  0	    0	  0	    0     0	    0     0	    0	  0	    0	  0	     0	     0	    0      0      0       0	    0	  0	    0	  0	     0	     0	    0      0      0        % ssee    X2  
      0	     0     1	  1     0	   0	  0	    0	  0	    0     0	    0	  0  	0	  0     0	  0	     0	     0	    0      0      0       0  	0	  0     0	  0	     0	     0	    0      0      0        % wsc     X3  
      0	     0	   0	  0	    0	   1	  0	    0	  0	    0     0	    0	  0	    0	  0	    0	  0	     0	     0	    0      0      0       0	    0	  0	    0	  0	     0	     0	    0      0      0        % ssc     X4 
      0	     0	   0	  0	    0	   0	  1     0	  0	    0     0 	0	  0	    0	  0	    0	  0	     0 	     0	    0      0      0       0	    0	  0	    0	  0	     0 	     0	    0      0      0        % srsee   X5   
      0	     0	   0	  0	    0	   0      0     1	  0	    0     0	    0     0	    0	  0	    0     0	     0	     0	    0      0      0       0	    0	  0	    0     0	     0	     0	    0      0      0        % srsc    X6  
      0	     0	   0	  0     0      0	  0	    1	  0	    0     1	    0	  0     0	  0     0	  0	     0	     0	    0      0      0       0     0	  0     0	  0	     0	     0	    0      0      0        % fsee    X7   
      0	     0	   0	  0	    1	   0	  0	    0	  1   -0.001  0	    0	  0	    0	  0	    0	  0	     0	     0      0      0      0       0	    0	  0	    0	  0	     0	     0      0      0      0        % psee    X8 
      1      0	   0	  0	    0	   0	  0	    0     0	    0     0	    0	  0	    0	  0	    0	  0   	 0       0	    0      0      0       0	    0	  0	    0	  0   	 0       0	    0      0      0        % esee    X9   
      0	     0	   0	  0	    0	   0	  0	    0	  0	    0   -0.9	1	  0	    0	  0     0	  0	     0	     0	    0      0      0       0	    0	  0     0	  0	     0	     0	    0      0      0        % srsb    X10 
      0	     0	   0	  0	    0      0	  0	    0	  0   	0     0	   0.01	  0	    0	  0     0	  0	     0	     0    	0      0      0       0	    0	  0     0	 0.01   0.01	 0    	0      0      0        % goalb   X11 
      0	     0	   0	  0	  -0.1     0	  0	    0	  0	    1     0	    0	  0     0	  0     0	  0      0	     0	    0      0      0       0     0	  0     0	  0      0	     0	    0      0      0        % psb     X12  
      0	     0	   0	  0	    0      0	  0	    0	  0	    0     0	    0	  0	    0	 0.1    0	  0	     0	     0	    0      0      0       0	    0	  0     0	  0	     0	     0	    0      0      0        % wsm1    X13 
      0	     0	   0	  0	    0      0	  0	    0	  0	    0     0	    0	  0	    1	  0     1	  0	     0	     0    	0      0      0       0	    0	  0     0	  0	     0	     0    	0      0      0        % wsm2    X14 
      0	     0	   0	  0	    0      0	  0	    0	  0	    0     0	    0	  0 	0	  0     0	  0      0 	     0    	0     0.1     0       0 	0	  0     0	  0      0 	     0    	0      0      0        % ssm1    X15 
      0	     0	   0	  0	    0      0	  0	    0	  0	    0     0	    0	  0	    0	  0     0	  0	     0	     0    	0      0      0       1	    0	  0     0	  0	     0	     0    	0      0      0        % ssm2    X16 
      0	     0	   0	  0	    0      0	  0	    0	  0	    0     0	    0	  0	    0	  0     0	  0  	 0	    0.1     0      0      0       0	    0	  0     0	  0  	 0	     0      0      0      0        % ssin1   X17 
      0	     0	   0	  0	    0      0	  0	    0	  0	    0     0	    0	  0  	0	  0     0	  0	     0	     0      1      0      0       0  	0	  0     0	  0	     0	     0      0      0      0        % ssin2   X18 
      0	     0	   0	  0	    0      0	  0	    0	  0	    0     0	   0.01	  0	    0	  0     0	  0	     0	     0      0      0      0       0	    0	  1    -0.2	  1	     0	     0      0      0      0        % srsin1  X19 
      0	     0	   0	  0	    0      0	  0     0	  0 	0     0	   0.01	  0	    0	  0     0	  0      0	     0   	0      0      0       0	    0	-0.01   1	  0      1	     0   	0      0      0        % srsin2  X20 
      0	     0	   0	  0	    0      0	  0	    0	  0	    0     1	    0	  0	    0	  0     0	  0  	 0	     0   	0      0      0       0	    0	  0     0	 0.1  	 0	    0.1   	0      0      0        % srsm1   X21 
      0      0	   0	  0	    0	   0      0	    0	  0	    0	  0     0	  0  	0	  0	    0     0	     0  	 0	    0      0      0       0  	0	  0	    0    0.1	 0  	0.1	    0      0      0        % srse1   X22
      0      0	   0	  0	    0	   0      0	    0	  0	    0	  1     0	  0  	0	  0	    0     0	     0  	 0	    0      0      0       0  	0	  0	    0     0	     1  	 0	    1      0      0        % srsm2   X23 
      0      0	   0	  0	    0	   0      0	    0	  0	    0	  0     0	  0  	0	  0	    0     0	     0  	 0	    0      0      0       0  	0	  0	    0     0	     1  	 0	    1      0      0        % srse2   X24 
      0      0	   0	  0	    0	   0      0	   0.2	  0	    0	  0     0	  0  	0	  0	    0     0	     0  	 0	    0      0      0       0  	0	  0	  -0.15 -0.9     1  	 0	    0      0      0        % bsn     X25 
      0      0	   0	  0	    0	   0      0	   -1	  0	    0	  1     0	  0  	0	  0	    0     0	     0  	 0	    0      0      0       0  	0	-0.15	0     1	   -0.9  	 0	    0      0      0        % bsp     X26 
      0      0	   0	  0	    0	   0      0	    0	  0	    1	  0     0	  0  	0	  0	    0     0	     0  	 1	    0      0      0       0  	0	  0	    0     0	     0  	 0	    0     0.1     0        % psin1   X27 
      0      0	   0	  0	    0	   0      0	    0	  0	    1	  0     0	  0  	0	  0	    0     0	     0  	 0	    1      0      0       0  	0	  0	    0     0	     0  	 0	    0      0      1        % psin2   X28 
      0      0	   0	  0	    0	   0      0	    0	  0	   0.1	  0     0	  0  	0	  0	    0     0	     0  	 0	    0      0     0.1      0  	0	  0	    0     0	     0  	 0	    0      0      0        % pse1    X29 
      0      0	   0	  0	    0	   0      0	    0	  0	   0.1	  0     0	  0  	0	  0	    0     0	     0  	 0	    0      0      0       0  	1	  0	    0     0	     0  	 0	    0      0      0        % pse2    X30 
      0      0	   0	  0	    0	   0      0	    0	  0	    0	  0     0	  0  	0	  0	    0    0.1	 0  	 0	    0      0      0       0  	0	  0	    0     0	     0  	 0	    0      0      0        % esin1   X31
      0      0	   0	  0	    0	   0      0	    0	  0	    0	  0     0	  0  	0	  0	    0     0	     1  	 0	    0      0      0       0  	0	  0	    0     0	     0  	 0	    0      0      0        % esin2   X32
];
Sp_f=[0.5	0.5   0.5   0.5   0.5    0.5    0.5   0.5   0.5   0.09   0.1  0.5  0.02 0.02 0.001  0.0008 0.001  0.001  0.001  0.0005  0.001  0.001   0.0009  0.0005  0.001 0.001 0.001 0.01  0.005   0.01  0.001   0.5 ];

%    X1     X2    X3     X4     X5     X6     X7    X8    X9   X10   X11   X12   X13   X14   X15   X16   X17    X18     X19    X20    X21    X22     X23   X24   X25   X26   X27    X28     X29    X30    X31    X32                  
%   wsee   ssee   wsc    ssc  srsee   srsc   fsee  psee  esee  srsb goalb  psb   wsm1  wsm2  ssm1  ssm2  ssin1  ssin2  srsin1 srsin2 srsm1  srse1   srsm2 srse2  bsn   bsp  psin1  psin2    pse1   pse2  esin1  esin2       
O=[   1	     1	   1	  1	    0	   1	  1	    0	  1	    0     0	    0	  0	    0	  1	    1	  1	     1	     0	    0      1      1       1	    1	  0	    0	  0	     0	     0	    0      1      0        %identity    
      0	     0	   0	  0  	0	   0	  0	    0	  0	    0     0   	0	  0	    0	  0     0	  0	     0 	     0	    0      0      0       0	    0	  0     0	  0	     0 	     0	    0      0      0        % Sum func
      0	     0	   0	  0	    1	   0	  0	    1	  0	    1     0	    1	  0	    0	  0	    0	  0	     0	     1	    1      0      0       0	    0	  1	    1	  1	     1	     1	    1      0      0        % scaled sum func
      0	     0	   0	  0     2	   0	  0     2	  0     3    0     3	  0	    0	  0	    0	  0	     0	    1.1	    2      0      0       0	    0	  1	    1	 2.21	4.01	0.2	    2      0      0        % scaling factor 
      0	     0 	   0	  0	    0	   0	  0	    0	  0	    0     0	    0	  0	    0	  0	    0	  0	     0	     0	    0      0      0       0	    0	  0	    0	  0	     0	     0	    0      0      0        % normalised sum norm adnorsum  
      0	     0	   0	  0	    0	   0	  0	    0	  0	    0     0	    0	  0  	0	  0	    0	  0	     0	     0	    0      0      0       0  	0	  0	    0	  0	     0	     0	    0      0      0        % normalizing factor 
      0	     0	   0	  0	    0	   0	  0	    0	  0	    0     0	    0	  0	    0	  0	    0	  0	     0	     0	    0      0      0       0	    0	  0	    0	  0	     0	     0	    0      0      0        % adaptive normalised sum adnorsum    
      0      0	   0	  0	    0	   0	  0	    0	  0	    0     0	    0	  0  	0	  0	    0	  0	     0	     0	    0      0      0       0  	0	  0	    0	  0	     0	     0	    0      0      0        % simple logistic  slogistic
      0	     0	   0	  0	    0	   0	  0	    0	  0	    0     0  	0	  0	    0	  0	    0	  0	     0	     0	    0      0      0       0	    0	  0	    0	  0	     0	     0	    0      0      0        % advanced logistic alogistic 
      0	     0	   0	  0	    0	   0	  0	    0	  0   	0     0     0	  0     0	  0	    0	  0	     0	     0	    0      0      0       0     0	  0	    0	  0	     0	     0	    0      0      0        % steepness    
      0	     0	   0	  0	    0	   0	  0	    0	  0	    0     0	    0	  0  	0	  0	    0	  0	     0	     0	    0      0      0       0  	0	  0	    0	  0	     0	     0	    0      0      0        % threshold 
      0	     0	   0	  0	    0	   0	  0	    0	  0	    0     1  	0	  0  	1	  0	    0	  0	     0	     0	    0      0      0       0  	0	  0	    0	  0	     0	     0	    0      0      1        % adaptive advanced logistic adalogistic 
      0	     0	   0	  0	    0	   0	  0	    0	  0	    0    100    0	  0    80     0	    0	  0	     0	     0	    0      0      0       0 	0	  0  	0	  0	     0	     0	    0      0     100       % steepness   
      0	     0	   0	  0	    0	   0	  0	    0	  0	    0    0.4    0	  0    0.01   0	    0	  0	     0	     0	    0      0      0       0 	0 	  0	    0	  0	     0	     0	    0      0     0.25      % threshold factor 
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

eta(19,12)=0.5;
hebb(19,12)=0.7;
mu(19,12)=0.98;

eta(20,12)=0.5;
hebb(20,12)=0.7;
mu(20,12)=0.98;

%path to current file
path = strcat(cd, '/simulation/params.csv');

%user parameters
params = readtable(path);
wsc = params.stressEvent; % .95-1
bsp = params.positiveBelief; %.05-.6
% esee = params.stressLevel;

path = strcat(cd, '/simulation/output/simulanneal.mat');
%if speed factors created by parameter tuning exist
if(exist(path, 'file'))
    simulanneal = load(path);
    simulanneal = simulanneal.speed_factor;
    
    Sp_f(1, 1) = simulanneal(1, 1);%wsee
    Sp_f(1, 7) = simulanneal(1, 2);%fsee
    Sp_f(1, 9) = simulanneal(1, 3);%esee
    
    delete(path);
end

dt=1;
max_t = 8000;
time=0:dt:max_t;
L=length(time);
STDX=zeros(L,N);

STDX(1,3)=1;%wsc
%STDX(1,13)=0.2;
STDX(1,14)=0.02;%wsm2
STDX(1,11)=0.01;%goalb
STDX(1,32)=0.01;%esin2
%STDX(1,9)=0.081;

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
%             C7=wxsum(i-1,j);
            C11=0;
            for jj=1:N
                C11=C11+condy(i-1,(jj-1)*N+j);
            end
            landa(i-1,j)=C11;
%             C11=landa(i-1,j);
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

save(strcat(cd, '/simulation/output/connection_weights.mat'), 'W');
save(strcat(cd, '/simulation/output/state_initialization.mat'), 'state_initialization');
save(strcat(cd, '/simulation/output/combi_func.mat'), 'O');
save(strcat(cd, '/simulation/output/delta_t.mat'), 'dt');
save(strcat(cd, '/simulation/output/max_t.mat'), 'max_t');
save(strcat(cd, '/simulation/output/speed_factors.mat'), 'Sp_f')
save(strcat(cd, '/simulation/output/statedyn.mat'), 'STDX');
save(strcat(cd, '/simulation/output/eta.mat'), 'eta');
save(strcat(cd, '/simulation/output/hebb.mat'), 'hebb');
save(strcat(cd, '/simulation/output/mu.mat'), 'mu');

save(strcat(cd, '/simulation/data/', filename, '_results'), 'STDX');