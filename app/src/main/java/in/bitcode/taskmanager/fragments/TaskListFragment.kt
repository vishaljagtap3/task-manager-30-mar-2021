package `in`.bitcode.taskmanager.fragments

import `in`.bitcode.taskmanager.R
import `in`.bitcode.taskmanager.adapters.TasksAdapter
import `in`.bitcode.taskmanager.db.DBUtil
import `in`.bitcode.taskmanager.models.Task
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TaskListFragment : Fragment() {

    private var mRecyclerTasks : RecyclerView? = null
    private var mListTasks: ArrayList<Task>? = null
    private var mTasksAdapter: TasksAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.task_list_fragment, null)
        mRecyclerTasks = view.findViewById(R.id.recyclerTasks)

        //set up the RV
        var dbUtil = DBUtil(activity as Context)
        mListTasks = dbUtil.getAllTasks()
        dbUtil.close()

        mTasksAdapter = TasksAdapter(mListTasks)
        mRecyclerTasks?.adapter = mTasksAdapter

        mRecyclerTasks?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        return view;
    }
}