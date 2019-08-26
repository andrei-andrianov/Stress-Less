 function SSR = parameter_tuning (P)
    state_initialization = load(strcat(cd, '/simulation/output/state_initialization'));
    state_initialization = state_initialization.state_initialization;
    
    speed_factors = P;
     
    Output = BDiSNModel(state_initialization, speed_factors);
    
    Empirical_Data = load(strcat(cd, '/simulation/output/statedyn'));
    emp_data = Empirical_Data.STDX;
    
    [o_row,o_col] = size(Output);
    
    SSR = sqrt(nansum (nansum ((Output - emp_data) .^2 )) / (o_col * o_row));
 end

function statedyn = BDiSNModel(state_initialization, spoody)

    connection_values = load(strcat(cd, '/simulation/output/connection_weights'));
    connection_values=connection_values.W; 

    speed_factors = load(strcat(cd, '/simulation/output/speed_factors'));
    speed_factors = speed_factors.Sp_f;
    
    speed_factors(1, 1) = spoody(1, 1);%wsee
    speed_factors(1, 7) = spoody(1, 2);%fsee
    speed_factors(1, 9) = spoody(1, 3);%esee
    
    combi_func = load(strcat(cd, '/simulation/output/combi_func'));
    combi_func = combi_func.O;
    
    delta_t = load(strcat(cd, '/simulation/output/delta_t'));
    delta_t = delta_t.dt;
    max_t=load(strcat(cd, '/simulation/output/max_t'));
    max_t=max_t.max_t;
    
    %adaptation connections
    ad_eta = load(strcat(cd, '/simulation/output/eta'));
    eta = ad_eta.eta;
    ad_hebb = load(strcat(cd, '/simulation/output/hebb'));
    hebb = ad_hebb.hebb;
    ad_mu = load(strcat(cd, '/simulation/output/mu'));
    mu = ad_mu.mu;
     
    N = length(connection_values);
    
    slhom = zeros(N);
    alhom = zeros(N);
    sqhom = zeros(N);
    aqhom = zeros(N);
    threshold = zeros(N);
    amplification = zeros(N);
    adcon = zeros(14+N,N ^ 2);
      
    time=1:delta_t:max_t;
    Length = length(time);
    statedyn = zeros(Length,N);
    statedyn(1,:) = state_initialization;
    STDX=statedyn;
   
    htau=threshold;
    amp=amplification;

    id=combi_func(1,:); 
    sum=combi_func(2,:);
    ssum=combi_func(3,:);
    lambda=combi_func(4,:);
    norsum=combi_func(5,:);
    norlambda=combi_func(6,:);
    adnorsum=combi_func(7,:);
    slog=combi_func(8,:);
    alog=combi_func(9,:);
    s=combi_func(10,:);
    t=combi_func(11,:);
    adalog=combi_func(12,:);
    adas=combi_func(13,:);
    adat=combi_func(14,:);

    k=0;    
    for i=1:N
        for j=1:N
            k=k+1;
        condy(1,k)=connection_values(i,j);
        end
    end


    for i=2:Length
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
                    C1)+slhom(ii,jj)*(C1+amp(ii,jj)*C1*C2*...
                   C3)+alhom(ii,jj)*(C1+amp(ii,jj)*C2*((C4+C3)/2)+C1*...
                   ((C4-C3)/2))+sqhom(ii,jj)*(C1+amp(ii,jj)*C1*C2*C5)+...
                   aqhom(ii,jj)*(C1*amp(ii,jj)*C2*((C6+C5)/2)+C1*((C6-C5)/2))-...
                   C1)*delta_t;       
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

            STDX(i,j)=STDX(i-1,j)+speed_factors(j)*(aggimpact(i-1,j)-STDX(i-1,j))*delta_t;
            end
     end
   end
    statedyn = STDX;
end