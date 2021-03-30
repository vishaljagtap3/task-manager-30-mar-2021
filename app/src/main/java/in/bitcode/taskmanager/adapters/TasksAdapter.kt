package `in`.bitcode.taskmanager.adapters

import `in`.bitcode.taskmanager.R
import `in`.bitcode.taskmanager.models.Task
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TasksAdapter : RecyclerView.Adapter<TasksAdapter.TaskHolder> {
    private var mListTasks: ArrayList<Task>? = null

    constructor(listTasks: ArrayList<Task>?) {
        mListTasks = listTasks
    }

    class TaskHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mTxtTaskTile : TextView
        var mTxtTaskAddedOn : TextView

        init {
            mTxtTaskTile = view.findViewById(R.id.txtTaskTitle)
            mTxtTaskAddedOn = view.findViewById(R.id.txtTaskAddedOn)
        }
    }

    override fun getItemCount(): Int {
        if ( mListTasks != null) {
            return mListTasks!!.size
        }

        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.task_view, null )
        return TaskHolder(view)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        var task = mListTasks?.get(position)

        holder.mTxtTaskTile.setText( task?.getTitle())
        holder.mTxtTaskAddedOn.setText( "${task?.getAddedOn()}" )
    }
}