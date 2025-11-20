package com.example.todo;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0001\"B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0012\u0010\u0016\u001a\u00020\u00132\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0014J$\u0010\u0019\u001a\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u001b2\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\u00130\u001dH\u0002J\b\u0010\u001f\u001a\u00020\u0013H\u0002J\u0010\u0010 \u001a\u00020\u00132\u0006\u0010!\u001a\u00020\u0005H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\f\u001a\u00020\r8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006#"}, d2 = {"Lcom/example/todo/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "allTasks", "", "Lcom/example/todo/model/Task;", "b", "Lcom/example/todo/databinding/ActivityMainBinding;", "currentQuery", "", "currentSort", "Lcom/example/todo/MainActivity$SortMode;", "vm", "Lcom/example/todo/ui/TaskViewModel;", "getVm", "()Lcom/example/todo/ui/TaskViewModel;", "vm$delegate", "Lkotlin/Lazy;", "applyFilterAndSort", "", "adapter", "Lcom/example/todo/ui/TaskAdapter;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "pickDateTime", "calendar", "Ljava/util/Calendar;", "onPicked", "Lkotlin/Function1;", "", "showAddDialog", "showEditTaskDialog", "task", "SortMode", "app_debug"})
public final class MainActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.example.todo.databinding.ActivityMainBinding b;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy vm$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.example.todo.model.Task> allTasks;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String currentQuery = "";
    @org.jetbrains.annotations.NotNull()
    private com.example.todo.MainActivity.SortMode currentSort = com.example.todo.MainActivity.SortMode.BY_ORDER;
    
    public MainActivity() {
        super();
    }
    
    private final com.example.todo.ui.TaskViewModel getVm() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void applyFilterAndSort(com.example.todo.ui.TaskAdapter adapter) {
    }
    
    private final void showAddDialog() {
    }
    
    private final void showEditTaskDialog(com.example.todo.model.Task task) {
    }
    
    private final void pickDateTime(java.util.Calendar calendar, kotlin.jvm.functions.Function1<? super java.lang.Long, kotlin.Unit> onPicked) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/example/todo/MainActivity$SortMode;", "", "(Ljava/lang/String;I)V", "BY_ORDER", "PRIORITY_HIGH_FIRST", "DUE_SOONEST_FIRST", "DUE_LATEST_FIRST", "app_debug"})
    static enum SortMode {
        /*public static final*/ BY_ORDER /* = new BY_ORDER() */,
        /*public static final*/ PRIORITY_HIGH_FIRST /* = new PRIORITY_HIGH_FIRST() */,
        /*public static final*/ DUE_SOONEST_FIRST /* = new DUE_SOONEST_FIRST() */,
        /*public static final*/ DUE_LATEST_FIRST /* = new DUE_LATEST_FIRST() */;
        
        SortMode() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public static kotlin.enums.EnumEntries<com.example.todo.MainActivity.SortMode> getEntries() {
            return null;
        }
    }
}