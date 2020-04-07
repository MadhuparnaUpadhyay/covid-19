package live.combatemic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import live.combatemic.R;

import live.combatemic.model.QuestionAnswer;

import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.BaseExpandableListAdapter;


public class QuestionAnswerExpandableListAdapter extends BaseExpandableListAdapter {

    private final List<QuestionAnswer.QuestionAnswerModel> items;
    private final QuestionAnswerFragment.OnListFragmentInteractionListener mListener;
    private Context _context;


    public QuestionAnswerExpandableListAdapter(Context context, List<QuestionAnswer.QuestionAnswerModel> items, QuestionAnswerFragment.OnListFragmentInteractionListener mListener) {
        this._context = context;
        this.items = items;
        this.mListener = mListener;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.items.get(groupPosition).answer;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.fragment_question_answer_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.question_answer);

        txtListChild.setText(childText);
        return convertView;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.items.get(groupPosition).question;
    }

    @Override
    public int getGroupCount() {
        return this.items.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.fragment_question_answer_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.question_header);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(groupPosition + 1 + ". " + headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}