/**
 * Created by nphau on 04/05/2022, 10:30
 * Copyright (c) 2021 nphau. All rights reserved.
 * Last modified 04/05/2022, 15:54
 */
package sg.nphau.java.owl.utils;

import org.springframework.lang.NonNull;

public interface Executor<T> {
    void execute(@NonNull T result);
}
