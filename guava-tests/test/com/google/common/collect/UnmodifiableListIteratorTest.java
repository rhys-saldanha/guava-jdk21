/*
 * Copyright (C) 2010 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.common.collect;

import static com.google.common.collect.ReflectionFreeAssertThrows.assertThrows;

import com.google.common.annotations.GwtCompatible;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import junit.framework.TestCase;
import org.jspecify.annotations.NullMarked;

/**
 * Tests for UnmodifiableListIterator.
 *
 * @author Louis Wasserman
 */
@GwtCompatible
@NullMarked
public class UnmodifiableListIteratorTest extends TestCase {
  @SuppressWarnings("DoNotCall")
  public void testRemove() {
    Iterator<String> iterator = create();

    assertTrue(iterator.hasNext());
    assertEquals("a", iterator.next());
    assertThrows(UnsupportedOperationException.class, () -> iterator.remove());
  }

  @SuppressWarnings("DoNotCall")
  public void testAdd() {
    ListIterator<String> iterator = create();

    assertTrue(iterator.hasNext());
    assertEquals("a", iterator.next());
    assertEquals("b", iterator.next());
    assertEquals("b", iterator.previous());
    assertThrows(UnsupportedOperationException.class, () -> iterator.add("c"));
  }

  @SuppressWarnings("DoNotCall")
  public void testSet() {
    ListIterator<String> iterator = create();

    assertTrue(iterator.hasNext());
    assertEquals("a", iterator.next());
    assertEquals("b", iterator.next());
    assertEquals("b", iterator.previous());
    assertThrows(UnsupportedOperationException.class, () -> iterator.set("c"));
  }

  UnmodifiableListIterator<String> create() {
    String[] array = {"a", "b", "c"};

    return new UnmodifiableListIterator<String>() {
      int i;

      @Override
      public boolean hasNext() {
        return i < array.length;
      }

      @Override
      public String next() {
        if (!hasNext()) {
          throw new NoSuchElementException();
        }
        return array[i++];
      }

      @Override
      public boolean hasPrevious() {
        return i > 0;
      }

      @Override
      public int nextIndex() {
        return i;
      }

      @Override
      public String previous() {
        if (!hasPrevious()) {
          throw new NoSuchElementException();
        }
        return array[--i];
      }

      @Override
      public int previousIndex() {
        return i - 1;
      }
    };
  }
}
