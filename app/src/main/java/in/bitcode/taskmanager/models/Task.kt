package `in`.bitcode.taskmanager.models

import java.io.Serializable

class Task(private val mId: Long,
           private var mTitle: String,
           private val mAddedOn: Long,
           private var mStatus: Boolean
) : Serializable {

    companion object {
        final var KEY_TASK = "task"
    }

    public fun getId(): Long = mId
    public fun getTitle(): String = mTitle
    public fun getStatus() : Boolean = mStatus
    public fun getAddedOn(): Long = mAddedOn

    public fun setTitle(title : String) {
        mTitle = title
    }

    public fun markDone(status: Boolean) {
        mStatus = status;
    }
}