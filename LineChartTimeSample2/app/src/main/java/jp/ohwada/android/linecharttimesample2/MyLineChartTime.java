/**
 * line chart 
 * 2017-11-01 K.OHWADA
 */

package jp.ohwada.android.linecharttimesample2;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.SeekBar;


import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * class MyLineChartTime
 */
public class MyLineChartTime {


    private LineChart mChart;
private XAxis mXAxis;
private YAxis mLeftAxis;
  private ArrayList<Entry> mValues;

    
    /**
 * === constructor ===
 */
public  MyLineChartTime( LineChart chart ) {
                
          mChart = chart;              

        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        mChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);
        mChart.setHighlightPerDragEnabled(true);

        // set an alternative background color
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setViewPortOffsets(0f, 0f, 0f, 0f);


        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();
        l.setEnabled(false);

// XAxis
        mXAxis = mChart.getXAxis();
        
        mXAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);

        mXAxis.setTextSize(10f);
        mXAxis.setTextColor(Color.WHITE);
        mXAxis.setDrawAxisLine(false);
        mXAxis.setDrawGridLines(true);
        mXAxis.setTextColor(Color.rgb(255, 192, 56));
        mXAxis.setCenterAxisLabels(true);
        mXAxis.setGranularity(1f); // one hour
        
        mXAxis.setValueFormatter(new IAxisValueFormatter() {

            private SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM HH:mm");

            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                long millis = TimeUnit.HOURS.toMillis((long) value);
                return mFormat.format(new Date(millis));
            }
        });
        

// AxisLeft
        mLeftAxis = mChart.getAxisLeft();
        
        mLeftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);


        mLeftAxis.setTextColor(ColorTemplate.getHoloBlue());
        mLeftAxis.setDrawGridLines(true);
        mLeftAxis.setGranularityEnabled(true);        
        mLeftAxis.setTextColor(Color.rgb(255, 192, 56));

    // AxisRight
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);

    mValues = new ArrayList<Entry>();
    }


    /**
 * setLeftAxisMinimum
 */
public void setLeftAxisMinimum(float min) {
    if ( mLeftAxis != null ) {
        mLeftAxis.setAxisMinimum( min );
    } // if
    
        } // setLeftAxisMinimum   
   
       /**
 * setLeftAxisMaximum
 */     
 public void setLeftAxisMaximum(float max) {
      mLeftAxis.setAxisMaximum( max );
}

       /**
 * setLeftAxisOffset
 */ 
 public void setLeftAxisOffset(float offset) {
        //mLeftAxis.setYOffset( offset );
}

       /**
 * setXAxiTypeface
 */
    public void setXAxiTypeface( Typeface tf ) {
        mXAxis.setTypeface(tf);
}
 
 
        /**
 * setLeftAxisTypeface
 */
     public void setLeftAxisTypeface( Typeface tf ) {
         mLeftAxis.setTypeface(tf);
    }
 
  
         /**
 * toggleValues
 */   
            public void toggleValues() {   
             List<ILineDataSet> sets = mChart.getData()
                      .getDataSets();

             for (ILineDataSet iSet : sets) {

                 LineDataSet set = (LineDataSet) iSet;
                 set.setDrawValues(!set.isDrawValuesEnabled());
             } // for
    
            mChart.invalidate();
            
           } // toggleValues
           

   
            /**
 * toggleHighlight
 */       
    public void toggleHighlight() {
            if (mChart.getData() != null) {
                 mChart.getData().setHighlightEnabled(!mChart.getData().isHighlightEnabled());
                 mChart.invalidate();
              } // for
              
        } // toggleHighlight


            /**
 * toggleFilled
 */            
        public void toggleFilled() {  
               List<ILineDataSet> sets = mChart.getData()
                        .getDataSets();

              for (ILineDataSet iSet : sets) {

                   LineDataSet set = (LineDataSet) iSet;
                if (set.isDrawFilledEnabled())
                      set.setDrawFilled(false);
                  else
                       set.setDrawFilled(true);
              
              } // for
              
                mChart.invalidate();
                
        } // oggleFilled
        
        
      
             /**
 * toggleCircles
 */                  
    public void toggleCircles() {
                List<ILineDataSet> sets = mChart.getData()
                        .getDataSets();

                for (ILineDataSet iSet : sets) {

                    LineDataSet set = (LineDataSet) iSet;
                    if (set.isDrawCirclesEnabled())
                        set.setDrawCircles(false);
                    else
                       set.setDrawCircles(true);
                
                } // for
                mChart.invalidate();
                
    } // toggleCircles
    
    
                /**
 * toggleCubic
 */     
              public void toggleCubic() {
                List<ILineDataSet> sets = mChart.getData()
                        .getDataSets();

                for (ILineDataSet iSet : sets) {

                   LineDataSet set = (LineDataSet) iSet;
                    if (set.getMode() == 
                     LineDataSet.Mode.CUBIC_BEZIER)
                        set.setMode(LineDataSet.Mode.LINEAR);
                    else
                       set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
               
                } // for
                
                mChart.invalidate();
            
            } // toggleCubic
            
            
                /**
 * toggleCubic
 */
    public void toggleStepped() {
               List<ILineDataSet> sets = mChart.getData()
                      .getDataSets();


                for (ILineDataSet iSet : sets) {

                  LineDataSet set = (LineDataSet) iSet;
                  
                   if (set.getMode() == 
                    LineDataSet.Mode.STEPPED)
                         set.setMode(LineDataSet.Mode.LINEAR);
                    else
                         set.setMode(LineDataSet.Mode.STEPPED);
               
               } // for
               
                mChart.invalidate();
    
    } // toggleStepped
    
    

                 /**
 * togglePinch
 */               
    public void togglePinch() {
                
                if (mChart.isPinchZoomEnabled())
                    mChart.setPinchZoom(false);
                 else
                    mChart.setPinchZoom(true);

                mChart.invalidate();
                
    } // togglePinch
    
    

                 /**
 * toggleAutoScaleMinMax
 */                 
    public void toggleAutoScaleMinMax() {
                
                mChart.setAutoScaleMinMaxEnabled(!
                 mChart.isAutoScaleMinMaxEnabled());
                 mChart.notifyDataSetChanged();
                 
        } // toggleAutoScaleMinMax
      
        
                   /**
 * animateX
 */       
        public void animateX( int x ) {
                 mChart.animateX(x);
                 
            } // animateX
            
             
                    /**
 * animateY
 */                
        public void animateY( int y ) {
                 mChart.animateY(y);
                 
            } // animateY
            
            
                    /**
 * animateXY
 */  
    public void animateXY( int x, int y ) {
                 mChart.animateXY( x, y );
                 
                 } // animateXY
                 
                 

                     /**
 * saveToPath
 */                
        public boolean saveToPath() {
            return mChart.saveToPath( ("title" +
                  System.currentTimeMillis() ), "" );
                
                } // saveToPath
                

                      /**
 * saveToGallery
 */               
                  public boolean saveToGallery() {           
                    return mChart.saveToGallery( ("title"+System.currentTimeMillis ()), 0 );
                
                } // saveToGallery
                

                      /**
 * saveToGallery
 */   
    public void procProgressChanged(SeekBar seekBar, int progress, boolean fromUser ) {

    //        tvX.setText("" + (mSeekBarX.getProgress()));

        // setData(mSeekBarX.getProgress(), 50);

        // redraw
        mChart.invalidate();
    }
    

 
                         /**
 * addData
 */    
    public void addData( float x, float y ) {
          mValues.add(new Entry(x, y));
    
    } // addData
        
                                /**
 * setData
 */   
            public void setData() {
        
        // create a dataset and give it a type

                 LineDataSet set1 = new LineDataSet(mValues, "DataSet 1");


                 set1.setAxisDependency(AxisDependency.LEFT);
         set1.setColor(ColorTemplate.getHoloBlue());
         set1.setValueTextColor(ColorTemplate.getHoloBlue());
         set1.setLineWidth(1.5f);
         set1.setDrawCircles(false);
         set1.setDrawValues(false);
         set1.setFillAlpha(65);
         set1.setFillColor(ColorTemplate.getHoloBlue());
         set1.setHighLightColor(Color.rgb(244, 117, 117));
         set1.setDrawCircleHole(false);

        // create a data object with the datasets
         LineData data = new LineData(set1);
         data.setValueTextColor(Color.WHITE);
         data.setValueTextSize(9f);

        // set data
         mChart.setData(data);
        
        } // setData

} // MyLineChartTime