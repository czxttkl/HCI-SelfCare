package io.github.czxttkl.game.graph;

import io.github.czxttkl.game.create.ChallengeActivity;
import io.github.czxttkl.game.help.HelpViewpager;
import io.github.czxttkl.game.mainscreen.MainScreenActivity;
import io.github.czxttkl.game.model.Challenge;
import io.github.czxttkl.game.model.ChallengeLab;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.chart.BubbleChart;
import org.achartengine.chart.CubicLineChart;
import org.achartengine.chart.LineChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.model.XYValueSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.actionbarsherlock.sample.shakespeare.R;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ChartFragment extends Fragment {
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.help_on_menu, menu);

	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.menu_help:
			Intent helpIntent = new Intent(getActivity(), HelpViewpager.class);
			helpIntent.putExtra("page", 3);
			startActivity(helpIntent);
			return true;
		case android.R.id.home:
			Intent intent = new Intent(getActivity(), MainScreenActivity.class);
			startActivity(intent);
			getActivity().finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.mainscreen_chart, container, false);
	}

	@Override
	public void onResume() {
		super.onResume();
		drawChart();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

	}

	private void drawChart() {
		String[] titles = new String[] { "Time Spent In Activities"};
		List<double[]> x = new ArrayList<double[]>();
		for (int i = 0; i < titles.length; i++) {
			x.add(new double[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 });
		}
		List<double[]> values = new ArrayList<double[]>();
		values.add(new double[] { 12.3, 12.5, 13.8, 16.8, 20.4, 24.4, 26.4, 26.1, 23.6, 20.3, 17.2, 13.9 });
		int[] colors = new int[] { Color.GREEN };
		PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE, PointStyle.DIAMOND };
		XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
		renderer.setPointSize(5.5f);
		int length = renderer.getSeriesRendererCount();
		for (int i = 0; i < length; i++) {
			XYSeriesRenderer r = (XYSeriesRenderer) renderer.getSeriesRendererAt(i);
			r.setLineWidth(5);
			r.setFillPoints(true);
		}
		setChartSettings(renderer, "Your Activeness Per Week", "Week", "Hours", 0.5, 12.5, 0, 40, Color.LTGRAY, Color.LTGRAY);

		renderer.setXLabels(12);
		renderer.setYLabels(10);
		renderer.setShowGrid(true);
		renderer.setXLabelsAlign(Align.RIGHT);
		renderer.setYLabelsAlign(Align.RIGHT);
		renderer.setZoomButtonsVisible(true);
		renderer.setPanLimits(new double[] { -10, 20, -10, 40 });
		renderer.setZoomLimits(new double[] { -10, 20, -10, 40 });

		XYValueSeries sunSeries = new XYValueSeries("Updates Count");
		sunSeries.add(1f, 35, 4.3);
		sunSeries.add(2f, 35, 4.9);
		sunSeries.add(3f, 35, 5.9);
		sunSeries.add(4f, 35, 8.8);
		sunSeries.add(5f, 35, 10.8);
		sunSeries.add(6f, 35, 11.9);
		sunSeries.add(7f, 35, 13.6);
		sunSeries.add(8f, 35, 12.8);
		sunSeries.add(9f, 35, 11.4);
		sunSeries.add(10f, 35, 9.5);
		sunSeries.add(11f, 35, 7.5);
		sunSeries.add(12f, 35, 5.5);
		XYSeriesRenderer lightRenderer = new XYSeriesRenderer();
		lightRenderer.setColor(Color.YELLOW);

		XYSeries waterSeries = new XYSeries("Scores");
		waterSeries.add(1, 16);
		waterSeries.add(2, 15);
		waterSeries.add(3, 16);
		waterSeries.add(4, 17);
		waterSeries.add(5, 20);
		waterSeries.add(6, 23);
		waterSeries.add(7, 25);
		waterSeries.add(8, 25.5);
		waterSeries.add(9, 26.5);
		waterSeries.add(10, 24);
		waterSeries.add(11, 22);
		waterSeries.add(12, 18);
		renderer.setBarSpacing(0.5);
		XYSeriesRenderer waterRenderer = new XYSeriesRenderer();
		waterRenderer.setColor(Color.argb(250, 0, 210, 250));

		XYMultipleSeriesDataset dataset = buildDataset(titles, x, values);
		dataset.addSeries(0, sunSeries);
		dataset.addSeries(0, waterSeries);
		renderer.addSeriesRenderer(0, lightRenderer);
		renderer.addSeriesRenderer(0, waterRenderer);
		waterRenderer.setDisplayChartValues(true);
		waterRenderer.setChartValuesTextSize(10);

		renderer.setMarginsColor(Color.WHITE);
		
		String[] types = new String[] { BarChart.TYPE, BubbleChart.TYPE, LineChart.TYPE };
		GraphicalView chartView = ChartFactory.getCombinedXYChartView(getActivity(), dataset, renderer, types);
		
		LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.mainscreen_chart);
		// layout.removeAllViews();
		layout.addView(chartView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	}

	private XYMultipleSeriesRenderer buildRenderer(int[] colors, PointStyle[] styles) {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.setAxisTitleTextSize(16);
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(15);
		renderer.setLegendTextSize(15);
		renderer.setPointSize(5f);
		renderer.setMargins(new int[] { 20, 30, 15, 0 });
		int length = colors.length;
		for (int i = 0; i < length; i++) {
			XYSeriesRenderer r = new XYSeriesRenderer();
			r.setColor(colors[i]);
			r.setPointStyle(styles[i]);
			renderer.addSeriesRenderer(r);
		}
		return renderer;
	}

	private void setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle, String yTitle,
			double xMin, double xMax, double yMin, double yMax, int axesColor, int labelsColor) {
		renderer.setChartTitle(title);
		renderer.setXTitle(xTitle);
		renderer.setYTitle(yTitle);
		renderer.setXAxisMin(xMin);
		renderer.setXAxisMax(xMax);
		renderer.setYAxisMin(yMin);
		renderer.setYAxisMax(yMax);
		renderer.setAxesColor(axesColor);
		renderer.setLabelsColor(labelsColor);
	}

	protected XYMultipleSeriesDataset buildDataset(String[] titles, List<double[]> xValues, List<double[]> yValues) {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		int length = titles.length;
		for (int i = 0; i < length; i++) {
			TimeSeries series = new TimeSeries(titles[i]);
			double[] xV = xValues.get(i);
			double[] yV = yValues.get(i);
			int seriesLength = xV.length;
			for (int k = 0; k < seriesLength; k++) {
				series.add(xV[k], yV[k]);
			}
			dataset.addSeries(series);
		}
		return dataset;
	}
}
