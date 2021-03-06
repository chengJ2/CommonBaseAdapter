
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;

/**
 * 适配器的抽象基类   数据形式为List
 * @author chengJ2
 *
 * @param <T>
 */
public  abstract class CommonBaseAdapter<T> extends BaseAdapter {
	protected LayoutInflater inflater;
	protected List<T> mList;
	protected Context context;
	protected int LayoutID;
	
	private CommFilter mNameFilter;
	protected List<T> mFilteredArrayList; // 过滤功能
	
	public CommonBaseAdapter(Context context,List<T> mList,int LayoutID){
		this.context=context;
		this.mList=mList;
		this.LayoutID=LayoutID;
		inflater=LayoutInflater.from(context);
		mFilteredArrayList = new ArrayList<T>();
	}
	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public T getItem(int position) {
		
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}
	
	public Filter getFilter() {
		if (mNameFilter == null) {
			mNameFilter = new CommFilter();
		}
		return mNameFilter;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=ViewHolder.getViewHolder(context, convertView, position, LayoutID, parent);
		convert(holder, getItem(position),position);
		return holder.getConvertView();
	}
	public abstract void convert(ViewHolder holder,T item,int position);
	
	
	private class CommFilter extends Filter{

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults filterResults = new FilterResults();
			for (Iterator<T> iterator = mList.iterator(); iterator.hasNext();) {
		        T name = iterator.next();
		        if (((List<T>) name).contains(constraint)) {
		        	mList.add(name);
		        }
		      }
		      filterResults.values = mList;
		      return filterResults;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
				mList = (List<T>) results.values;
		      if (results.count > 0) {
		        notifyDataSetChanged();
		      } else {
		        notifyDataSetInvalidated();
		      }
		}
		
	};
	
}
