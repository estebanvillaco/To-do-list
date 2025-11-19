package com.example.todo.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J%\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012\u00a2\u0006\u0002\u0010\u0013J\u000e\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u0016J\u0016\u0010\u0017\u001a\u00020\f2\u0006\u0010\u0018\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u0016J\u000e\u0010\u001a\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u001c"}, d2 = {"Lcom/example/todo/ui/TaskViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/example/todo/data/TaskRepository;", "(Lcom/example/todo/data/TaskRepository;)V", "tasks", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/example/todo/model/Task;", "getTasks", "()Lkotlinx/coroutines/flow/StateFlow;", "addTask", "", "title", "", "prio", "Lcom/example/todo/model/Priority;", "dueDate", "", "(Ljava/lang/String;Lcom/example/todo/model/Priority;Ljava/lang/Long;)V", "deleteAt", "position", "", "move", "from", "to", "toggleDone", "id", "app_debug"})
public final class TaskViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.todo.data.TaskRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.todo.model.Task>> tasks = null;
    
    public TaskViewModel(@org.jetbrains.annotations.NotNull()
    com.example.todo.data.TaskRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.todo.model.Task>> getTasks() {
        return null;
    }
    
    public final void addTask(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    com.example.todo.model.Priority prio, @org.jetbrains.annotations.Nullable()
    java.lang.Long dueDate) {
    }
    
    public final void toggleDone(@org.jetbrains.annotations.NotNull()
    java.lang.String id) {
    }
    
    public final void deleteAt(int position) {
    }
    
    public final void move(int from, int to) {
    }
}