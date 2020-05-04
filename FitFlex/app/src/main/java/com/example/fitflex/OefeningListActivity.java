package com.example.fitflex;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fitflex.dummy.DummyContent;

import java.util.List;
import java.util.Objects;

/**
 * An activity representing a list of Oefeningen. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link OefeningDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class OefeningListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oefening_list);

        Toolbar toolbar = findViewById(R.id.oefeningToolbar);
        setSupportActionBar(toolbar);
        if (findViewById(R.id.oefening_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.oefening_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull final RecyclerView recyclerView) {

        new DummyContent().listenDataLoadChange(new DummyContent.OnDataLoadListener() {
            @Override
            public void onDataLoaded(List<DummyContent.DummyItem> dummyItems) {
                recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(OefeningListActivity.this, dummyItems, mTwoPane));
            }
        });

    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final OefeningListActivity mParentActivity;
        private final List<DummyContent.DummyItem> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DummyContent.DummyItem item = (DummyContent.DummyItem) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(OefeningDetailFragment.ARG_ITEM_ID, item.id);
                    OefeningDetailFragment fragment = new OefeningDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.oefening_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, OefeningDetailActivity.class);
                    intent.putExtra(OefeningDetailFragment.ARG_ITEM_ID, item.id);
                    intent.putExtra(OefeningDetailFragment.ARG_ITEM_NAME, item.naam);
                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(OefeningListActivity parent,
                                      List<DummyContent.DummyItem> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.oefening_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(String.valueOf(position+1));
            holder.mContentView.setText(mValues.get(position).naam);
            holder.mLevelView.setText(mValues.get(position).moeilijkheid);

            if (mValues.get(position).moeilijkheid.equals("Makkelijk")) {

                holder.mLevelView.setTextColor(Color.parseColor("#009e2a"));

            } else if (mValues.get(position).moeilijkheid.equals("Gemiddeld")) {

                holder.mLevelView.setTextColor(Color.parseColor("#bfc900"));

            } else {

                holder.mLevelView.setTextColor(Color.parseColor("#b50000"));

            }

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;
            final TextView mLevelView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
                mLevelView = (TextView) view.findViewById(R.id.moeilijkheid);
            }
        }
    }
}
