package com.csprojectback.freelork.model;

public class ViewModel {
    public static class Public { }
    static class ExtendedPublic extends Public { }
    public static class Internal extends ExtendedPublic { }
}
