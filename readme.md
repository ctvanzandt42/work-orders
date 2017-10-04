<h1>Work Order Processing</h1>

For this activity one process will be creating work orders and writing them to disk. Another process will read the work orders and then process them.

The work order process is to first put the work order in the INITIAL pile. It next moves to the ASSIGNED pile (think of this as when the work order is assigned to someone). Next it moves to the IN_PROGRESS pile (when the person it was assigned to actually starts working on it.). And lastly it moves to the DONE pile (when the work is done).