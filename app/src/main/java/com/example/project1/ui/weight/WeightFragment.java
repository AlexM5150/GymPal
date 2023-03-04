package com.example.project1.ui.weight;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.project1.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class WeightFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Creates the view for the graph to show up
        View view = inflater.inflate(R.layout.fragment_weight, container, false);
        // Gets graph based of the id made in the xml file
        GraphView graph = (GraphView) view.findViewById(R.id.idGraphView);
        // Testing graph, can add data points as needed
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new
                DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);
        return view;
    }
}
