package `in`.bitcode.taskmanager.fragments

import `in`.bitcode.taskmanager.R
import `in`.bitcode.taskmanager.adapters.TasksAdapter
import `in`.bitcode.taskmanager.db.DBUtil
import `in`.bitcode.taskmanager.models.Task
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TaskListFragment : Fragment() {

    private var mRecyclerTasks: RecyclerView? = null
    private var mListTasks: ArrayList<Task>? = null
    private var mTasksAdapter: TasksAdapter? = null

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

        //set up the RV
        var dbUtil = DBUtil(activity as Context)
        mListTasks = dbUtil.getAllTasks()
        dbUtil.close()

        mTasksAdapter = TasksAdapter(mListTasks)
        mRecyclerTasks?.adapter = mTasksAdapter

        mRecyclerTasks?.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        return view;
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
            mListTasks?.add(task)
            mTasksAdapter?.notifyDataSetChanged()
        }

        override fun onFailure() {

        }
    }
}