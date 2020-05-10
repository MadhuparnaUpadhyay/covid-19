package live.combatemic.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import live.combatemic.app.R;

import live.combatemic.app.model.QuestionAnswer;

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
        lblListHeader.setText(groupPosition + 1 + ". " + headerTitle);

        ImageView upImage = (ImageView) convertView
                .findViewById(R.id.expandable_up);
        ImageView downImage = (ImageView) convertView
                .findViewById(R.id.expandable_down);

        if (isExpanded) {
            upImage.setVisibility(View.VISIBLE);
            downImage.setVisibility(View.GONE);
        } else {
            upImage.setVisibility(View.GONE);
            downImage.setVisibility(View.VISIBLE);
        }

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