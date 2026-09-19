package com.stuardop.gym;

import java.io.IOException;
import java.util.List;

public interface WorkoutStorage {
    List<WorkoutSet> load() throws IOException;
    void save(List<WorkoutSet> sets) throws IOException;
}

