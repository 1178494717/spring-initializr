package com.lph.initializr.model;

/**
 * @date 2020/1/8
 */
public enum DependencyScope {

    /**
     * A dependency that is used as an annotation processor when compiling a project.
     */
    ANNOTATION_PROCESSOR,

    /**
     * A dependency that is used to compile a project.
     */
    COMPILE,

    /**
     * A dependency that is a compile time only dependency and not used at runtime.
     */
    COMPILE_ONLY,

    /**
     * A dependency this is used to run a project.
     */
    RUNTIME,

    /**
     * A dependency that is provided and is used to run the project.
     */
    PROVIDED_RUNTIME,

    /**
     * A dependency that is used to compile a project's tests.
     */
    TEST_COMPILE,

    /**
     * A dependency this is used to run a project's tests.
     */
    TEST_RUNTIME

}