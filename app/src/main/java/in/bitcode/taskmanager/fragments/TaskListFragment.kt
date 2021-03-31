package `in`.bitcode.taskmanager.fragments

import `in`.bitcode.taskmanager.R
import `in`.bitcode.taskmanager.adapters.TasksAdapter
import `in`.bitcode.taskmanager.db.DBUtil
import `in`.bitcode.taskmanager.models.Task
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.Serializable

class TaskListFragment : Fragment() {

    private var mRecyclerTasks: RecyclerView? = null
    private var mListTasks: ArrayList<Task>
    private var mTasksAdapter: TasksAdapter? = null

    private var mSelectedTaskIndex = -1

    init {
        mListTasks = ArrayList<Task>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.task_list_fragment, null)
        mRecyclerTasks = view.findViewById(R.id.recyclerTasks)

        var dbUtil = DBUtil(activity as Context)
        mListTasks = dbUtil.getAllTasks()
        Log.e("tag", "" + mListTasks.size)
        dbUtil.close()

        //set up the RV
        mTasksAdapter = TasksAdapter(mListTasks)
        mRecyclerTasks?.adapter = mTasksAdapter
        mTasksAdapter?.mOnTaskClickListener = OnTaskClickListener()



        mRecyclerTasks?.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        return view;
    }

    inner class OnTaskClickListener : TasksAdapter.OnTaskClickListener {
        override fun onTaskClick(task: Task, position: Int) {

            mSelectedTaskIndex = position

            var taskDetailsFragment = TaskDetailsFragment()
            var input = Bundle()
            input.putSerializable(Task.KEY_TASK, task as Serializable)
            taskDetailsFragment.arguments = input

            taskDetailsFragment.mOnTaskStateListener = OnTaskStateListener()

            fragmentManager?.beginTransaction()
                ?.add(R.id.mainContainer, taskDetailsFragment)
                ?.addToBackStack(null)
                ?.commit()

        }
    }

    private inner class OnTaskStateListener : TaskDetailsFragment.OnTaskStateListener {
        override fun onDelete(task: Task?) {
            if(mSelectedTaskIndex != -1) {
                mListTasks.removeAt(mSelectedTaskIndex)
                mTasksAdapter?.notifyItemRemoved(mSelectedTaskIndex)
                mSelectedTaskIndex = -1
            }
        }

        override fun onUpdate(updatedTask: Task) {

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.task_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuAddTask -> {

                var newTaskFragment = NewTaskFragment()
                newTaskFragment.setOnTaskListener(MyOnTaskListener())

                fragmentManager?.beginTransaction()
                    ?.add(R.id.mainContainer, newTaskFragment, null)
                    ?.addToBackStack(null)
                    ?.commit()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private inner class MyOnTaskListener : NewTaskFragment.OnTaskListener {
        override fun onSuccess(task: Task) {
            mListTasks.add(task)
            mTasksAdapter?.notifyDataSetChanged()
        }

        override fun onFailure() {

        }
    }
}