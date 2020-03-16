package com.example.screens.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.screens.data.Result;
import com.example.screens.R;
import com.example.screens.api.ApiFactory;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Result> results;
    private OnPosterClickListener onPosterClickListener;
    private OnReachEndListener onReachEndListener;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    public void addMovies(List<Result> results) {
        this.results.addAll(results);
        notifyDataSetChanged();
    }

    public interface OnPosterClickListener {
        void onPosterClick(int position);
    }

    public interface OnReachEndListener {
        void onReachEnd();

    }

    public void setOnPosterClickListener(OnPosterClickListener onPosterClickListener) {
        this.onPosterClickListener = onPosterClickListener;
    }

    public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        if (results.size() >= 20 && position > this.results.size() - 2 && onReachEndListener != null) {
            onReachEndListener.onReachEnd();
        }
        Result result = this.results.get(position);
//        holder.textViewLabelNameValue.setText(result.getTitle());
//        holder.textViewRateValue.setText(String.format(" %s", result.getVoteAverage()));
        Picasso.get().load(ApiFactory.moviePosterPathBuilder(true, result.getPosterPath())).into(holder.imageViewSmallPoster);

    }

    @Override
    public int getItemCount() {
        if (results == null) {
            return 0;
        }
        return results.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        //        private TextView textViewLabelNameValue;
//        private TextView textViewRateValue;
        private ImageView imageViewSmallPoster;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
//            textViewLabelNameValue = itemView.findViewById(R.id.textViewLabelNameValue);
//            textViewRateValue = itemView.findViewById(R.id.textViewRateValue);
            imageViewSmallPoster = itemView.findViewById(R.id.imageViewSmallPoster);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onPosterClickListener != null) {
                        onPosterClickListener.onPosterClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
