import android.content.Context;
import android.text.Html;
import android.text.InputType;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

/**
 * 适配器优化辅助类
 * @author chengJ2
 *
 */
public class ViewHolder {
	private final SparseArray<View> mViews;
	private View convertview;
	
	private ImageLoader mImageLoader; // 异步下载图片
	private boolean mBusy = false;
	
	private ViewHolder(Context context, int position, int LayoutId,
			ViewGroup parent){
		mViews=new SparseArray<View>();
		convertview=LayoutInflater.from(context).inflate(LayoutId, null);
		convertview.setTag(this);
		mImageLoader=new ImageLoader(context);
	}
	public static ViewHolder getViewHolder(Context context, View convertView,
			int position, int LayoutId, ViewGroup parent){
		if(convertView==null){
			return new ViewHolder(context, position, LayoutId, parent);
		}else{
			return (ViewHolder) convertView.getTag();
		}
	}
	
	public View getConvertView(){
		return convertview;
	}
	
	public ViewHolder setFlagBusy(boolean busy) {
		this.mBusy = busy;
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int ViewID){
		View view=mViews.get(ViewID);
		if(view==null){
			view=convertview.findViewById(ViewID);
			mViews.put(ViewID, view);
		}
		return (T) view;
	}
	
	public void setItemHeight(int height){
		convertview.setLayoutParams(new AbsListView.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,height));
	}
	
	public void setItembgColor(int color){
		convertview.setBackgroundColor(color);
	}
	
	public void setItembgResource(int resid){
		convertview.setBackgroundResource(resid);
	}
	
	public void setItemEnable(boolean enable){
		convertview.setEnabled(enable);
	}
	
	public ViewHolder setText(int ViewID,String str){
		TextView tv=getView(ViewID);
		tv.setText(str);
		return this;
	}
	
	public ViewHolder setTextSingleLine(int ViewID,boolean singleline,int maxlines){
		TextView tv=getView(ViewID);
		tv.setSingleLine(singleline);
		tv.setMaxLines(maxlines);
		return this;
	}
	
	public ViewHolder setTextSingleLine(int ViewID,boolean singleline){
		setTextSingleLine(ViewID,false,2);
		return this;
	}
	
	public ViewHolder setTextForHtml(int ViewID,String str){
		TextView tv=getView(ViewID);
		if (str != null && !"".equals(str)) {
			tv.setText(Html.fromHtml(str));
		}else{
			tv.setText(str);
		}
		return this;
	}
	
	public EditText getEditText(int ViewID){
		EditText tv=getView(ViewID);
		return tv;
	}
	
	public ViewHolder setTextHint(int ViewID,String str){
		EditText tv=getView(ViewID);
		tv.setHint(str);
		return this;
	}
	
	
	public ViewHolder setInputType(int ViewID){
		EditText tv=getView(ViewID);
		tv.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
		return this;
	}
	
	public ViewHolder setTextVisibe(int ViewID,int visible){
		TextView tv=getView(ViewID);
		tv.setVisibility(visible);
		return this;
	}
	
	public ViewHolder setEditText(int ViewID,String str){
		EditText tv=getView(ViewID);
		tv.setText(str);
		return this;
	}
	
	public ViewHolder setEditTextVisibe(int ViewID,int visible){
		EditText tv=getView(ViewID);
		tv.setVisibility(visible);
		return this;
	}
	
	public ViewHolder setEditTextEnable(int ViewID,boolean enable){
		EditText tv=getView(ViewID);
		tv.setEnabled(enable);
		return this;
	}
	
	public ViewHolder setRadioGroupVisibe(int ViewID,int visible){
		RadioGroup rg=getView(ViewID);
		rg.setVisibility(visible);
		return this;
	}
	
	public ViewHolder setRadioGroupEnable(int ViewID,boolean enable){
		RadioGroup rg=getView(ViewID);
		rg.setEnabled(enable);
		return this;
	}
	
	public ViewHolder setRadioGroupChoose(int radiogroupId,final RadiogroupChecked radiogroupChecked){
		RadioGroup rg=getView(radiogroupId);
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int arg1) {
				int radioButtonId = group.getCheckedRadioButtonId();
				RadioButton rb = getView(radioButtonId);
				radiogroupChecked.checkedValue(rb);
			}
		});
		return this;
	}
	
	public ViewHolder setRadioButtonChoose(int radiobuttonId){
		RadioButton rb = getView(radiobuttonId);
		rb.setChecked(true);
		return this;
	}
	
	
	public interface RadiogroupChecked{
		void checkedValue(RadioButton rb);
	}
	
	
	public ViewHolder setTextColor(int ViewID,int color){
		TextView tv=getView(ViewID);
		tv.setTextColor(color);
		return this;
	}

	public ViewHolder setImageResource(int ViewID,int resID){
		ImageView iv=getView(ViewID);
		iv.setImageResource(resID);
		return this;
	}
	
	public ViewHolder setButtonVisibe(int ViewID,int visible){
		Button iv=getView(ViewID);
		iv.setVisibility(visible);
		return this;
	}
	
	public ViewHolder setImageVisibe(int ViewID,int visible){
		ImageView iv=getView(ViewID);
		iv.setVisibility(visible);
		return this;
	}
	
	public ViewHolder setBackgroundResource(int ViewID,int resID){
		ImageView iv=getView(ViewID);
		iv.setBackgroundResource(resID);
		return this;
	}
	
	public ViewHolder showImage(int ViewID,String url){
		ImageView iv=getView(ViewID);
		if(!"".equals(url)&&url!=null){
			iv.setVisibility(View.VISIBLE);
			if(!mBusy){
				mImageLoader.DisplayImage(url, iv, false);
			}else{
				mImageLoader.DisplayImage(url, iv, true);
			}
		}
		return this;
	}
	
	
	public ViewHolder setOnClickEvent(int ViewID,final ClickEvent c){
		View v=getView(ViewID);
		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				c.onClick(v);
			}
		});
		return this;
	}
	
	public ViewHolder setOnLongClickEvent(int ViewID,final ClickEvent c){
		View v=getView(ViewID);
		v.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				c.onClick(v);
				return true;
			}
		});
		return this;
	}
	
	public interface ClickEvent{
		void onClick(View view);
	}
}
