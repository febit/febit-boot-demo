package org.febit.boot.demo.doggy.config;

import lombok.experimental.UtilityClass;
import org.febit.boot.demo.doggy.model.auth.AppAuth;
import org.febit.boot.demo.doggy.model.auth.AppAuthImpl;

@UtilityClass
public class Auths {

    public static final AppAuth ANONYMOUS = AppAuthImpl.builder()
            .id(401L)
            .code("anonymous")
            .displayName("Anonymous")
            .build();

    public static final AppAuth SYSTEM = AppAuthImpl.builder()
            .id(101L)
            .code("system")
            .displayName("System")
            .build();

    public static final AppAuth ADMIN = AppAuthImpl.builder()
            .id(201L)
            .code("admin")
            .displayName("Admin")
            .build();

}
