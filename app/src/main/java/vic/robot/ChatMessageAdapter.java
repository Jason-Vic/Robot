package vic.robot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import vic.bean.ChatMessage;

/**
 * Created by 38035 on 2016/8/11.
 */
public class ChatMessageAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List mlist;

    public ChatMessageAdapter(Context context, List list) {
        layoutInflater = LayoutInflater.from(context);
        mlist = list;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int i) {
        return mlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public int getItemViewType(int position) {
        ChatMessage chatMessage = (ChatMessage) mlist.get(position);
        if (chatMessage.getType() == ChatMessage.Type.INCOMING) {
            return 0;
        }
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ChatMessage chatMessage = (ChatMessage) mlist.get(i);
        ViewHolder viewHolder;

        if (view == null) {
                if (getItemViewType(i) == 0) {
                view = layoutInflater.inflate(R.layout.item_from_msg, viewGroup, false);
                viewHolder = new ViewHolder();
                viewHolder.mDate = (TextView) view.findViewById(R.id.id_from_msg_date);
                viewHolder.mMsg = (TextView) view.findViewById(R.id.id_from_msg_info);

            } else {
                view = layoutInflater.inflate(R.layout.item_to_msg, viewGroup, false);
                viewHolder = new ViewHolder();
                viewHolder.mDate = (TextView) view.findViewById(R.id.id_to_msg_date);
                viewHolder.mMsg = (TextView) view.findViewById(R.id.id_to_msg_info);
            }
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        viewHolder.mDate.setText(dateFormat.format(chatMessage.getDate()));
        viewHolder.mMsg.setText(chatMessage.getMsg());
        return view;
    }


    private final class ViewHolder {
        TextView mDate;
        TextView mMsg;
    }
}
