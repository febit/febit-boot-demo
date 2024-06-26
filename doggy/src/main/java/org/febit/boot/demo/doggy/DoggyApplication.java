/*
 * Copyright 2023-present febit.org (support@febit.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.febit.boot.demo.doggy;

import org.febit.boot.EnableFebitAuth;
import org.febit.boot.FebitBoot;
import org.febit.boot.demo.doggy.auth.config.IAuthConfigPkg;
import org.febit.boot.demo.doggy.config.IAppConfigPkg;
import org.febit.boot.module.FebitModuleEnvironments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@EnableFebitAuth
@SpringBootApplication
@ConfigurationPropertiesScan(basePackageClasses = {
        IAppConfigPkg.class,
        IAuthConfigPkg.class
})
@FebitModuleEnvironments(value = DoggyApiVersion.class)
@FebitModuleEnvironments(value = FebitBoot.class, prefix = "febit-boot")
public class DoggyApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoggyApplication.class, args);
    }
}
