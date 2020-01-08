package com.example.myapplication1214;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.AbstractChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import java.util.ArrayList;
import java.util.List;



public class PieChartView extends GraphicalView
{

    public static final int COLOR_GREEN = Color.parseColor("#62c51a");
    public static final int COLOR_ORANGE = Color.parseColor("#ff6c0a");
    public static final int COLOR_BLUE = Color.parseColor("#23bae9");

    /**
     * Constructor that only calls the super method. It is not used to instantiate the object from outside of this
     * class.
     *
     * @param context
     * @param arg1
     */
    private PieChartView(Context context, AbstractChart arg1)
    {
        super(context, arg1);
    }

    /**
     * This method returns a new graphical view as a pie chart view. It uses the income and costs and the static color
     * constants of the class to create the chart.
     *
     * @param context
     *            the context
     * @return a GraphicalView object as a pie chart
     */
    public static GraphicalView getNewInstance(Context context, ArrayList<Post>L)
    {
        return ChartFactory.getPieChartView(context, getDataSet(context, L), getRenderer(L));
    }

    /**
     * Creates the renderer for the pie chart and sets the basic color scheme and hides labels and legend.
     *
     * @return a renderer for the pie chart
     */
    private static DefaultRenderer getRenderer(ArrayList<Post> L)
    {
        int[] colors;
        if (L.get(0).isIncome()){
            Log.d("TTTT","#"+L.get(0).getItem_name());
            colors = new int[] { COLOR_GREEN, COLOR_ORANGE, COLOR_BLUE ,Color.RED};
        }else{
            colors = new int[] { COLOR_GREEN, COLOR_ORANGE, COLOR_BLUE ,Color.RED,Color.YELLOW,Color.MAGENTA,Color.LTGRAY};
        }


        DefaultRenderer defaultRenderer = new DefaultRenderer();
        for (int color : colors)
        {
            SimpleSeriesRenderer simpleRenderer = new SimpleSeriesRenderer();
            simpleRenderer.setColor(color);
            defaultRenderer.addSeriesRenderer(simpleRenderer);
        }
        defaultRenderer.setShowLabels(true);
        defaultRenderer.setLabelsTextSize(30);
        defaultRenderer.setShowLegend(false);
        return defaultRenderer;
    }

    /**
     * Creates the data set used by the piechart by adding the string represantation and it's integer value to a
     * CategorySeries object. Note that the string representations are hard coded.
     *
     * @param context
     *            the context
     *
     * @return a CategorySeries instance with the data supplied
     */
    private static CategorySeries getDataSet(Context context, ArrayList<Post> L)
    {
        double[] amount;
        CategorySeries series = new CategorySeries("Chart");
        if (L.get(0).isIncome()){
            amount = new double[]{0,0,0,0,0};
        }else{
            amount = new double[]{0,0,0,0,0,0,0,0};
        }
//        series.add("Income:", income);
//        series.add("Costs:", costs);
//        series.add("Total", income - costs);
        for (Post i : L){
            if (i.isIncome()){
                switch (i.getType()){
                    case "Salary":
                        amount[0] = amount[0]+i.getAmount();
                        continue;
                    case "Allowance":
                        amount[1] = amount[1]+i.getAmount();
                        continue;
                    case "Award or Bonus":
                        amount[2] = amount[2]+i.getAmount();
                        continue;
                    default:
                        amount[3] = amount[3]+i.getAmount();

                }
                amount[4] = amount[4] + i.getAmount();
            }
            else{
                switch (i.getType()){
                    case "Food":
                        amount[0] = amount[0]+i.getAmount();
                        continue;
                    case "Clothing":
                        amount[1] = amount[1]+i.getAmount();
                        continue;
                    case "Housing":
                        amount[2] = amount[2]+i.getAmount();
                        continue;
                    case "Transportation":
                        amount[3] = amount[3]+i.getAmount();
                        continue;
                    case "Education":
                        amount[4] = amount[4]+i.getAmount();
                        continue;
                    case "Entertainment":
                        amount[5] = amount[5]+i.getAmount();
                        continue;
                    default:
                        amount[6] = amount[6]+i.getAmount();
                }
                amount[7] = amount[7] + i.getAmount();

            }
        }
        if (L.get(0).isIncome()){
            series.add("Salary: $"+amount[0],amount[0]);
            series.add("Allowance: $"+amount[1],amount[1]);
            series.add("Award/Bonus: $"+amount[2],amount[2]);
            series.add("Others: $" +amount[3],amount[3]);
        }
        else {
            series.add("Food: $"+amount[0],amount[0]);
            series.add("Clothing: $"+amount[1],amount[1]);
            series.add("Housing: $"+amount[2],amount[2]);
            series.add("Transportation: $"+amount[3],amount[3]);
            series.add("Education: $"+amount[4],amount[4]);
            series.add("Entertainment: $"+amount[5],amount[5]);
            series.add("Others: $"+amount[6],amount[6]);

        }
        return series;

    }
}