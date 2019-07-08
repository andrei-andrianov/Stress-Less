clc
clear

close all
format long
N=24;

% for i=1:N
%     for j=1:N
%                 W(i,j)=input(['W_',num2str(i),'_',num2str(j)]);
%     end
% end


%    X1     X2     X3     X4    X5     X6     X7    X8    X9   X10   X11   X12   X13   X14   X15   X16   X17    X18     X19    X20    X21    X22   X23     X24 
%   wsee   ssee   wsc    ssc  srsee   srsc   fsee  psee  esee  wsm  srsb   psb   wsm   ssm   srsl srslun srsab srsim    epi    cort  pslaug  psab  psin   eslaugh    
W=[   0	     1	   0	  0	    0      0      0	    0	  0	    0     0  	0	  0	    0	  0     0 	  0	     0	     0   	0      0      0   	0       0    % wsee    X1  
      0      0     0	  0     1	   0	  0	    0	  0	    0     0	    0     0	    0	  0	    0	  0	     0	     0	    0      0      0   	0       0    % ssee    X2  
      0	     0     1	  1     0	   0	  0	    0	  0	    0     0	    0	  0  	0	  0     0	  0	     0	     0	    0      0      0   	0       0    % wsc     X3  
      0	     0	   0	  0	    0	   1	  0	    0	  0	    0     0	    0	  0	    0	  0	    0	  0	     0	     0	    0      0      0   	0       0    % ssc     X4 
      0	     0	   0	  0	    0	   0	  1     0	  0	    1     0 	0	  0	    0	  0	    0	  0	     0 	     0	    0      0      0   	0       0    % srsee   X5   
      0	     0	   0	  0	    0	   0      0     1	  0	    0     0	    0     0	    0	  0	    0     0	     0	     0	    0      0      0   	0       0    % srsc    X6  
      0	     0	   0	  0     0      0	  0	    1	  0	    0     0 	0     0	    0     0	    0     0	     0	     0	    0	   0      0   	0       0    % fsee    X7   
      0	     0	   0	  0	    1	   0	  0	    0	  1     0   -0.01	0	  0	    0	  0	    0	  0	     0	     0      0      0      0   	0       0    % psee    X8 
      1      0	   0	  0	    0	   0	  0	    0     0	    0     0	    0	  0	    0	  0	    0	  0   	 0       0	    0      0      0   	0       0    % esee    X9   
      0	     0	   0	  0	    0	   0	  0	    0	  0	    0     0	    1	  0	    0	  0     0	  0	     0	     0	    0      0      0   	1       0    % wsm    X10 
      0	     0	   0	  0	    0      0	  0	  -0.5	  0   	0     0	    1	  0	    0	  0     0	  0	     0	     0    	0      0      0   	0       0    % srsb    X11 
      0	     0	   0	  0	 -0.0005   0	  0	    0	  0	    0     1	    0	  0     0	  0     0	  0      0	     0	    0      0      0   	0       0    % psb     X12  
      0	     0	   0	  0	    0      0	  0	    0	  0	    0     0	    0	  1	    1	  0     0	  0	     0	     0	    0      0      0   	0       0    % wsm     X13 
      0	     0	   0	  0	    0      0	  0	    0     0	    0     0	    0	  0	    0	  1     0	  0	     0	     0    	0      0      0   	0       0    % ssm     X14 
      0	     0	   0	  0	    0      0	  0	    0	  0	    0     0	    0	  0 	0	  0     0	  0      0 	     0    	0      1      1   	1       0    % srsl    X15 
      0	     0	   0	  0	    0      0	  0	    0	  0     0     0	    0	  0	    0	  0     0	  0	     0	     0    	0      1      0   	1       0    % srslun  X16 
      0	     0	   0	  0	    0      0	  0	    0	  0	    0     0	   0.3	  0	    0	  0     0	  0  	 0	     0      0      1      1   	0       0    % srsab   X17 
      0	     0	   0	  0	    0      0	  0	    0	  0	    0     0	    0	  0  	0	  0     0	  0	     0	     1     -0.01   0      0   	0       0    % srsim   X18 
      0	     0	   0	  0	    0      0	  0	  -0.9    0	    0     0	    0	  0	    0	  0     0	  0	     0	     0      0      0      0   	0       0    % epi     X19 
      0	     0	   0	  0	    0      0	  0   -0.1	  0 	0     0	    0	  0	    0	  0     0	  0      0	     0   	0      0      0   	0       0    % pslaug  X20 
      0	     0	   0	  0	    0      0	  0	    0	  0	    0     1	    0	  0	    0	  0     0	  1  	 1	     0   	0      0      0   	0       1    % pslaug  X21 
      0	     0	   0	  0	    0      0	  0	    0     0	    0     0	    0	  0	    0	  0     0	  0	     1	     0      0      1      0   	0       0    % psab    X22 
      0	     0	   0	  0	    0      0	  0     0	  0 	0     0	    0	  0	    0	  0     1	  0      0	     0   	0      0      0   	0       0    % psin    X23 
      0	     0	   0	  0	    0      0	  0	    0	  0	    0     0	    0	  0	    0	  1     0	  0  	 0	     0   	0      0      0   	0       0    % eslaugh X24 
];
Sp_f=[0.9  0.01 0.003  0.003 0.008  0.003 0.008   0.003 0.005  0.005 0.001  0.1   0.5   0.5   0.5   0.5   0.5    0.5    0.5    0.5    0.5    0.5  0.001    0.5   ];

%    X1     X2     X3     X4    X5     X6     X7    X8    X9   X10   X11   X12   X13   X14   X15   X16   X17    X18     X19    X20    X21    X22   X23    X24                        
%   wsee   ssee   wsc    ssc  srsee   srsc   fsee  psee  esee  wsm  srsb   psb   wsm   ssm   srsl srslun srsab srsim    epi    cort  pslaug  psab  psin  eslaugh
O=[   1	     1	   0	  1     0	   1	  1	    0	  1	    0     0	    0	  0	    1	  0	    1	  1	     0	     1	    0      0      0	    0      1 	            %identity    
      0	     0	   0	  0  	0	   0	  0	    0	  0	    0     0   	0	  0	    0	  0     0	  0	     0 	     0	    0      0      0	    0      0                % Sum func
      0	     0	   0	  0	    1	   0	  0	    1	  0	    0     1	    1	  0	    0	  1	    0	  0	     1	     0	    1      1      1	    1      0  	            % scaled sum func
      0	     0	   0	  0     2	   0	  0     2	  0     0     1     2	  0	    0	  2	    0	  0	     2	     0	    1      4      1	    2      0  	            % scaling factor 
      0	     0 	   0	  0	    0	   0	  0	    0	  0	    0     0	    0	  0	    0	  0	    0	  0	     0	     0	    0      0      0	    0      0                % normalised sum norm adnorsum  
      0	     0	   0	  0	    0	   0	  0	    0	  0	    0     0	    0	  0  	0	  0	    0	  0	     0	     0	    0      0      0	    0      0  	            % normalizing factor 
      0	     0	   0	  0	    0	   0	  0	    0	  0	    0     0	    0	  0	    0	  0	    0	  0	     0	     0	    0      0      0	    0      0  	            % adaptive normalised sum adnorsum    
      0      0	   0	  0	    0	   0	  0	    0	  0	    0     0	    0	  0  	0	  0	    0	  0	     0	     0	    0      0      0	    0      0  	            % simple logistic  slogistic
      0	     0	   0	  0	    0	   0	  0	    0	  0	    0     0  	0	  0	    0	  0	    0	  0	     0	     0	    0      0      0	    0      0   	            % advanced logistic alogistic 
      0	     0	   0	  0	    0	   0	  0	    0	  0   	0     0     0	  0     0	  0	    0	  0	     0	     0	    0      0      0	    0      0   	            % steepness    
      0	     0	   0	  0	    0	   0	  0	    0	  0	    0     0	    0	  0  	0	  0	    0	  0	     0	     0	    0      0      0	    0      0   	            % threshold 
      0	     0	   1	  0	    0	   0	  0	    0	  0	    1     0  	0	  1  	0	  0	    0	  0	     0	     0	    0      0      0	    0      0   	            % adaptive advanced logistic adalogistic 
      0	     0	  100	  0	    0	   0	  0	    0	  0	   100    0     0	 100    0     0	    0	  0	     0	     0	    0      0      0	    0      0    	        % steepness   
      0	     0	  0.9	  0	    0	   0	  0	    0	  0	   0.75   0     0	 0.7    0     0	    0	  0	     0	     0	    0      0      0	    0      0   	            % threshold factor 
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

eta(17,12)=0.8;

hebb(17,12)=0.99;

mu(17,12)=0.98;


dt=1;
time=0:dt:6000;
L=length(time);
STDX=zeros(L,N);
STDX(1,3)=1;
STDX(1,10)=0.01;
STDX(1,13)=0.01;

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

    
color_array = rand(N, 3); 
    for i = 1:N
        plot(time, STDX(:,i), 'color', color_array(i,:),'lineWidth',3)
        hold on
    end


for i=1:N
    if i<10
    ch(i,1:3)=['X',num2str(i),' '];
    else
         ch(i,1:4)=['X',num2str(i),' '];
    end
end
legend(ch)
grid on


