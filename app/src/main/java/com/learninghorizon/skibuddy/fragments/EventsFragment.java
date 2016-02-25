package com.learninghorizon.skibuddy.fragments;

import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learninghorizon.skibuddy.R;
import com.learninghorizon.skibuddy.adpaters.EventAdapter;

import com.learninghorizon.skibuddy.model.SkiEvent;

import com.learninghorizon.skibuddy.utility.DataUtil;

import java.util.List;

/**
 */
public class EventsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String ARG_PAGE = "ARG_PAGE";
    public static EventAdapter eventAdapter;
    private int mPage;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SessionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventsFragment newInstance(int page) {
        EventsFragment fragment = new EventsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        fragment.setArguments(args);
        return fragment;
    }

    public EventsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        RecyclerView recList = (RecyclerView) view.findViewById(R.id.eventList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        eventAdapter = new EventAdapter(createList(30),getActivity());
        eventAdapter.setHasStableIds(true);
        recList.setAdapter(eventAdapter);
        return view;
    }

    private List<SkiEvent> createList(int size) {

        /*List<SkiSession> result = new ArrayList<SkiSession>();
        for (int i=1; i <= size; i++) {
            SkiSession si = new SkiSession();
            si.setName(SessionInfo.NAME_PREFIX + i);
            si.setDistance(i * 5 + "Miles");
            si.setSessionDate(new Date().getTime());
            si.setAverageSpeed(10*i+"Mi/Hr");
            result.add(si);

        }*/
         DataUtil.getInstance().setTotalEvents(DataUtil.getInstance().getUser().getEventInvites());
        DataUtil.getInstance().setTotalEvents(DataUtil.getInstance().getUser().getCreatedEvents());
       // totalEvents.addAll(DataUtil.getInstance().getUser().getAttendingEvents());

        return DataUtil.getInstance().getTotalEvents();
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

  /*  @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onStart(){
        super.onStart();
        //notifyDataChange();
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public void notifyDataChange(){
        eventAdapter.notifyDataSetChanged();
    }
}
