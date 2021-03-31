package `in`.bitcode.taskmanager.fragments

import `in`.bitcode.taskmanager.R
import `in`.bitcode.taskmanager.db.DBUtil
import `in`.bitcode.taskmanager.models.Task
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import androidx.fragment.app.Fragment

class NewTaskFragment : Fragment() {

    private var mEdtTaskTitle: EditText? = null
    private var mTxtAddedOn: TextView? = null
    private var mRadioDone: RadioButton? = null
    private var mRadioPending: RadioButton? = null
    private var mBtnAddTask: Button? = null
    public var i = 10


    interface OnTaskListener {
        fun onSuccess(task: Task)
        fun onFailure()
    }

    private var mOnTaskListener: OnTaskListener? = null

    fun setOnTaskListener(onTaskListener: OnTaskListener) {
        mOnTaskListener = onTaskListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.new_task_fragment, null);
        view.setOnClickListener(
            View.OnClickListener {

            })

        mEdtTaskTitle = view.findViewById(R.id.edtTaskTitle)
        mTxtAddedOn = view.findViewById(R.id.txtAddedOn)
        mRadioDone = view.findViewById(R.id.radioDone)
        mRadioPending = view.findViewById(R.id.radioPending)
        mBtnAddTask = view.findViewById(R.id.btnAddNewTask)

        mTxtAddedOn?.setText("" + System.currentTimeMillis())

        mBtnAddTask?.setOnClickListener(
            BtnAddTaskClickListener()
        )

        return view;
    }

    private inner class BtnAddTaskClickListener : View.OnClickListener {
        override fun onClick(view: View?) {

            var dbUtil = DBUtil(view?.context)
            var taskId = dbUtil.addTask(
                mEdtTaskTitle?.text.toString(),
                mTxtAddedOn?.text.toString().toLong(),
                mRadioDone?.isChecked as Boolean
            )
            dbUtil.close()

            if (taskId != -1.toLong()) {

                fragmentManager?.beginTransaction()
                    ?.remove(this@NewTaskFragment)
                    ?.commit()

                if (mOnTaskListener != null) {
                    mOnTaskListener?.onSuccess(
                        Task(
                            taskId,
                            mEdtTaskTitle?.text.toString(),
                            mTxtAddedOn?.text.toString().toLong(),
                            mRadioDone?.isChecked as Boolean
                        )
                    )
                }

            } else {
                if (mOnTaskListener != null) {
                    mOnTaskListener?.onFailure()
                }
            }

        }
    }


}