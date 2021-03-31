package `in`.bitcode.taskmanager.fragments

import `in`.bitcode.taskmanager.R
import `in`.bitcode.taskmanager.models.Task
import android.os.Bundle
import android.view.*
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.w3c.dom.Text

class TaskDetailsFragment : Fragment() {

    private var mTask : Task? = null

    private var mTxtTaskTitle : TextView? = null
    private var mTxtAddedOn : TextView? = null
    private var mRadioDone : RadioButton? = null
    private var mRadioPending : RadioButton? = null

    interface OnTaskStateListener {
        fun onDelete(task: Task?)
        fun onUpdate(updatedTask: Task)
    }

    var mOnTaskStateListener: OnTaskStateListener? = null
        set(onTaskStateListener) {
            field = onTaskStateListener
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        mTask = arguments?.getSerializable(Task.KEY_TASK) as Task
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_task_state_update, menu)
        mt("onCreateOptionsMenu");
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        mt("onPrepareOptionsMenu");
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menuTaskDelete -> {
                if (mOnTaskStateListener != null) {
                    mOnTaskStateListener?.onDelete( mTask )
                    fragmentManager?.beginTransaction()
                        ?.remove(this)
                        ?.commit()
                }
            }
            R.id.menuTaskUpdate -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.task_details_fragment, null)

        mTxtTaskTitle = view.findViewById(R.id.txtTaskTitle)
        mTxtAddedOn = view.findViewById(R.id.txtAddedOn)
        mRadioDone = view.findViewById(R.id.radioDone)
        mRadioPending = view.findViewById(R.id.radioPending)

        mTxtTaskTitle?.text = mTask?.getTitle()
        mTxtAddedOn?.text = mTask?.getTitle()
        if(mTask?.getStatus() == true) {
            mRadioDone?.isChecked = true
        }
        else {
            mRadioPending?.isChecked = true
        }

        return view
    }

    fun mt(text : String) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
    }
}