package lcc.ishuhui.manager;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lcc.ishuhui.model.ChapterListModel;

/**
 * Created by lcc_luffy on 2016/5/27.
 */
public class ChapterListManager {
    private static ChapterListManager instance;
    private List<ChapterListModel.ReturnEntity.Chapter> chapters;
    private int currentIndex;

    private ChapterListManager() {
    }

    public static ChapterListManager instance() {
        if (instance == null)
            instance = new ChapterListManager();
        return instance;
    }

    public List<ChapterListModel.ReturnEntity.Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(Collection<ChapterListModel.ReturnEntity.Chapter> chapters, int currentIndex) {
        if (chapters == null)
            chapters = new ArrayList<>();
        this.chapters.clear();
        this.chapters.addAll(chapters);
        this.currentIndex = currentIndex;
    }

    @Nullable
    public ChapterListModel.ReturnEntity.Chapter nextChapter() {
        if (currentIndex > 0 && !chapters.isEmpty()) {
            currentIndex--;
            return chapters.get(currentIndex);
        }
        return null;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }
}
