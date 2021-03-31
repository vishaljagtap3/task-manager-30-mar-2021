package `in`.bitcode.taskmanager.adapters

import `in`.bitcode.taskmanager.R
import `in`.bitcode.taskmanager.models.Task
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class TasksAdapter(private var mListTasks: ArrayList<Task>) : RecyclerView.Adapter<TasksAdapter.TaskHolder>() {


    interface OnTaskClickListener {
        fun onTaskClick(task: Task, position: Int)
    }

    var mOnTaskClickListener: OnTaskClickListener? = null
        set(onTaskClickListener) {
            field = onTaskClickListener
        }


    inner class TaskHolder(view: View) : RecyclerView.ViewHolder(view) {

        var mTxtTaskTile: TextView = view.findViewById(R.id.txtTaskTitle)
        var mTxtTaskAddedOn: TextView = view.findViewById(R.id.txtTaskAddedOn)

        init {
            view.setOnClickListener(View.OnClickListener {
                Toast.makeText(it.context, "" + adapterPosition , Toast.LENGTH_LONG).show()
                if(mOnTaskClickListener != null) {
                    mOnTaskClickListener?.onTaskClick( mListTasks.get(adapterPosition), adapterPosition)
                }
            })
        }

    }

    override fun getItemCount(): Int {
        if (mListTasks != null) {
            return mListTasks!!.size
        }

        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.task_view, null)
        return TaskHolder(view)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        var task = mListTasks?.get(position)

        holder.mTxtTaskTile.text = task?.getTitle()
        holder.mTxtTaskAddedOn.text = "${task?.getAddedOn()}"
    }
}