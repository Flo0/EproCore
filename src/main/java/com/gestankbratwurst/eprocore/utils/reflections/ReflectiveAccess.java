package com.gestankbratwurst.eprocore.utils.reflections;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of HopperMachines and was created at the 16.04.2021
 *
 * HopperMachines can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class ReflectiveAccess<T> {

  private final Map<String, Field> fieldMap;
  private final Map<String, Method> methodMap;
  private final Class<T> tClass;

  public ReflectiveAccess(final Class<T> tClass) {
    this.fieldMap = new HashMap<>();
    this.methodMap = new HashMap<>();
    this.tClass = tClass;
  }

  public Object invokeMethod(final Object target, final String methodName, final Object... params) {
    final Method method = this.getTypedMethod(methodName, Arrays.stream(params).map(Object::getClass).toArray(Class<?>[]::new));
    if (method == null) {
      return null;
    }
    try {
      final boolean access = method.canAccess(target);
      if (!access) {
        method.setAccessible(true);
      }
      final Object returnValue = method.invoke(target, params);
      if (!access) {
        method.setAccessible(false);
      }
      return returnValue;
    } catch (final IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
      return null;
    }
  }

  public Object getFieldValue(final Object target, final String fieldName) {
    final Field field = this.fieldMap.computeIfAbsent(fieldName, this::getField);
    if (field == null) {
      return null;
    }
    if (field.trySetAccessible()) {
      try {
        return field.get(target);
      } catch (final IllegalAccessException e) {
        e.printStackTrace();
        return null;
      }
    } else {
      return null;
    }
  }

  public boolean setFieldValue(final Object target, final Object value, final String fieldName) {
    final Field field = this.fieldMap.computeIfAbsent(fieldName, this::getField);
    if (field == null) {
      return false;
    }
    if (field.trySetAccessible()) {
      try {
        field.set(target, value);
        return true;
      } catch (final IllegalAccessException e) {
        e.printStackTrace();
        return false;
      }
    } else {
      return false;
    }
  }

  public boolean hasMethod(final String name, final Class<?>... parameterTypes) {
    return this.getTypedMethod(name, parameterTypes) != null;
  }

  private Field getField(final String name) {
    try {
      return this.tClass.getDeclaredField(name);
    } catch (final NoSuchFieldException e) {
      return null;
    }
  }

  public boolean hasField(final String name) {
    return this.fieldMap.computeIfAbsent(name, this::getField) != null;
  }

  private Method getTypedMethod(final String methodName, final Class<?>... paramTypes) {
    final String typedName = methodName + Arrays.stream(paramTypes).map(Class::getName).collect(Collectors.joining("#"));
    return this.methodMap.computeIfAbsent(typedName, name -> this.getMethod(methodName, paramTypes));
  }

  private Method getMethod(final String name, final Class<?>... parameterTypes) {
    try {
      return this.tClass.getDeclaredMethod(name, parameterTypes);
    } catch (final NoSuchMethodException e) {
      return null;
    }
  }

}
