package org.onedigit.study.java.algo.trading;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class EsperService
{
    public static void main(String[] args)
    {
        Configuration cepConfig = new Configuration();
        cepConfig.addEventType("StockPrice", StockPrice.class.getName());

        EPServiceProvider cep = EPServiceProviderManager.getProvider(
                "myCEPENgine", cepConfig);
        final EPRuntime cepRT = cep.getEPRuntime();

        // Register EPL statement
        EPAdministrator cepAdmin = cep.getEPAdministrator();

        /*
         * EPStatement cepStatement = cepAdmin.createEPL(
         * "select * from StockPrice(symbol='AAPL').win:length(10) having avg(price) > 25.0"
         * );
         */
        // EPStatement cepStatement =
        // cepAdmin.createEPL("select avg(price) as AvgPrice from StockPrice.win:length(10)");
        EPStatement cepStatement = cepAdmin
                .createEPL("select avg(price) as AvgPrice from StockPrice.win:time(3)");

        cepStatement.addListener(new UpdateListener() {
            @Override
            public void update(EventBean[] newData, EventBean[] oldDate)
            {
                System.out.println("Event received: "
                        + newData[0].getUnderlying());
            }
        });

        StockTicker st = new StockTicker(30, 0.01, 20, 0.01, 0.01);
        st.addStockListener(new StockListener() {
            @Override
            public void priceTick(StockPrice event)
            {
                cepRT.sendEvent(new StockPrice(event.getTime(), event.getPrice()));      
            }
        });
        Thread t = new Thread(st);
        t.start();
    }
}
