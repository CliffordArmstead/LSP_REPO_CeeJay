package org.howard.edu.lsp.assignment6;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link IntegerSet}.
 * <p>
 * Each public method of {@code IntegerSet} has at least one test,
 * including normal and edge cases.
 */
public class IntegerSetTest {

    /**
     * Tests adding elements and checking containment and duplicate prevention.
     */
    @Test
    public void testAddAndContainsAndLength() {
        IntegerSet set = new IntegerSet();
        assertTrue(set.isEmpty());
        assertEquals(0, set.length());

        set.add(1);
        set.add(2);

        assertTrue(set.contains(1));
        assertTrue(set.contains(2));
        assertFalse(set.contains(3));
        assertEquals(2, set.length());

        // Try adding duplicate
        set.add(2);
        assertEquals(2, set.length(), "Set should not allow duplicates.");
    }

    /**
     * Tests that {@code clear()} removes all elements.
     */
    @Test
    public void testClear() {
        IntegerSet set = new IntegerSet();
        set.add(10);
        set.add(20);
        assertFalse(set.isEmpty());
        assertEquals(2, set.length());

        set.clear();
        assertTrue(set.isEmpty());
        assertEquals(0, set.length());
    }

    /**
     * Tests {@code largest()} for a typical set and checks that it does not modify the set.
     */
    @Test
    public void testLargestNormalCase() {
        IntegerSet set = new IntegerSet();
        set.add(3);
        set.add(1);
        set.add(7);
        set.add(5);

        assertEquals(7, set.largest());
        // Ensure contents unchanged
        assertEquals(4, set.length());
        assertTrue(set.contains(1));
        assertTrue(set.contains(3));
        assertTrue(set.contains(5));
        assertTrue(set.contains(7));
    }

    /**
     * Tests that {@code largest()} throws an exception for an empty set.
     */
    @Test
    public void testLargestOnEmptySetThrows() {
        IntegerSet empty = new IntegerSet();
        assertThrows(IllegalStateException.class, empty::largest);
    }

    /**
     * Tests {@code smallest()} for a typical set and checks that it does not modify the set.
     */
    @Test
    public void testSmallestNormalCase() {
        IntegerSet set = new IntegerSet();
        set.add(3);
        set.add(1);
        set.add(7);
        set.add(5);

        assertEquals(1, set.smallest());
        assertEquals(4, set.length());
    }

    /**
     * Tests that {@code smallest()} throws an exception for an empty set.
     */
    @Test
    public void testSmallestOnEmptySetThrows() {
        IntegerSet empty = new IntegerSet();
        assertThrows(IllegalStateException.class, empty::smallest);
    }

    /**
     * Tests equality: same elements in a different order, non-equal sets,
     * null, and different object types.
     */
    @Test
    public void testEquals() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);
        set1.add(2);
        set1.add(3);

        IntegerSet set2 = new IntegerSet();
        set2.add(3);
        set2.add(2);
        set2.add(1);

        IntegerSet set3 = new IntegerSet();
        set3.add(1);
        set3.add(2);

        assertTrue(set1.equals(set2), "Sets with same elements in any order should be equal.");
        assertTrue(set2.equals(set1));
        assertFalse(set1.equals(set3), "Sets with different elements should not be equal.");
        assertFalse(set1.equals(null), "Set should not be equal to null.");
        assertFalse(set1.equals("not a set"), "Set should not be equal to an unrelated object.");
    }

    /**
     * Tests {@code isEmpty()} under different conditions.
     */
    @Test
    public void testIsEmpty() {
        IntegerSet set = new IntegerSet();
        assertTrue(set.isEmpty());

        set.add(1);
        assertFalse(set.isEmpty());

        set.clear();
        assertTrue(set.isEmpty());
    }

    /**
     * Tests {@code remove(int)} for existing and non-existing values.
     */
    @Test
    public void testRemove() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(2);
        set.add(3);

        set.remove(2);
        assertFalse(set.contains(2));
        assertEquals(2, set.length());

        // Removing an element that is not there should do nothing
        set.remove(99);
        assertEquals(2, set.length());
        assertTrue(set.contains(1));
        assertTrue(set.contains(3));
    }

    /**
     * Tests {@code union(IntegerSet)} including mutation of this set and
     * that the other set remains unchanged.
     */
    @Test
    public void testUnion() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);
        set1.add(2);

        IntegerSet set2 = new IntegerSet();
        set2.add(2);
        set2.add(3);

        set1.union(set2);

        // set1 should now contain {1, 2, 3}
        assertEquals(3, set1.length());
        assertTrue(set1.contains(1));
        assertTrue(set1.contains(2));
        assertTrue(set1.contains(3));

        // set2 should be unchanged
        assertEquals(2, set2.length());
        assertTrue(set2.contains(2));
        assertTrue(set2.contains(3));
    }

    /**
     * Tests that union with itself does not introduce duplicates.
     */
    @Test
    public void testUnionWithSelf() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(2);

        set.union(set);
        assertEquals(2, set.length(), "Union with self must not create duplicates.");
        assertTrue(set.contains(1));
        assertTrue(set.contains(2));
    }

    /**
     * Tests {@code intersect(IntegerSet)} for overlapping and disjoint sets.
     */
    @Test
    public void testIntersect() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);
        set1.add(2);
        set1.add(3);

        IntegerSet set2 = new IntegerSet();
        set2.add(2);
        set2.add(3);
        set2.add(4);

        set1.intersect(set2);
        assertEquals(2, set1.length());
        assertTrue(set1.contains(2));
        assertTrue(set1.contains(3));
        assertFalse(set1.contains(1));
        assertFalse(set1.contains(4));

        // Now test disjoint intersection
        IntegerSet disjoint1 = new IntegerSet();
        disjoint1.add(1);
        disjoint1.add(2);

        IntegerSet disjoint2 = new IntegerSet();
        disjoint2.add(3);
        disjoint2.add(4);

        disjoint1.intersect(disjoint2);
        assertTrue(disjoint1.isEmpty(), "Intersection of disjoint sets should be empty.");
    }

    /**
     * Tests {@code diff(IntegerSet)} (this \ other).
     */
    @Test
    public void testDiff() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);
        set1.add(2);
        set1.add(3);

        IntegerSet set2 = new IntegerSet();
        set2.add(2);
        set2.add(4);

        set1.diff(set2);
        // set1 should now contain {1, 3}
        assertEquals(2, set1.length());
        assertTrue(set1.contains(1));
        assertTrue(set1.contains(3));
        assertFalse(set1.contains(2));
        assertFalse(set1.contains(4));
    }

    /**
     * Tests {@code complement(IntegerSet)} where this becomes (other \ this),
     * including the case where this and other are the same instance.
     */
    @Test
    public void testComplement() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);
        set1.add(2);

        IntegerSet set2 = new IntegerSet();
        set2.add(2);
        set2.add(3);
        set2.add(4);

        set1.complement(set2);
        // set1 should now be {3, 4}
        assertEquals(2, set1.length());
        assertTrue(set1.contains(3));
        assertTrue(set1.contains(4));
        assertFalse(set1.contains(1));
        assertFalse(set1.contains(2));

        // set2 should remain unchanged
        assertEquals(3, set2.length());
        assertTrue(set2.contains(2));
        assertTrue(set2.contains(3));
        assertTrue(set2.contains(4));
    }

    /**
     * Tests complement where the set is complemented with itself.
     * Result should be an empty set.
     */
    @Test
    public void testComplementWithSelf() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(2);
        set.add(3);

        set.complement(set);
        assertTrue(set.isEmpty(), "Complement of a set with itself should be empty.");
    }

    /**
     * Tests {@code toString()} output format for empty and non-empty sets.
     */
    @Test
    public void testToString() {
        IntegerSet empty = new IntegerSet();
        assertEquals("[]", empty.toString());

        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(2);
        set.add(3);
        // Order will follow insertion order: [1, 2, 3]
        assertEquals("[1, 2, 3]", set.toString());
    }
}
