package com.gestankbratwurst.eprocore.utils.reflections;

import java.util.HashMap;
import java.util.Map;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of HopperMachines and was created at the 16.04.2021
 *
 * HopperMachines can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class ReflectionHelper {

  private static final Map<Class<?>, ReflectiveAccess<?>> REFLECTIVE_ACCESS_MAP = new HashMap<>();

  @SuppressWarnings("unchecked")
  public static <T> ReflectiveAccess<T> getAccessFor(final Class<T> tClass) {
    return (ReflectiveAccess<T>) REFLECTIVE_ACCESS_MAP.computeIfAbsent(tClass, ReflectiveAccess::new);
  }

}
