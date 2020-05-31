package com.veemed.veedoc.fragments;

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
public interface FragmentInteractionListener {

    public static enum ACTION_TYPE {
        DEFAULT,
        GO_BACK,
        GO_FORWARD,
        PROCESS_FINISH
    }

    void onFragmentInteraction(String tag, ACTION_TYPE action_type);

}