// ***** This file is automatically generated from SequenceArbitrarySubsequence.java.jpp

package daikon.derive.ternary;

import daikon.*;
import daikon.derive.*;

import plume.*;

/*>>>
import org.checkerframework.checker.interning.qual.*;
import org.checkerframework.dataflow.qual.*;
*/

public final class SequenceFloatArbitrarySubsequence
  extends TernaryDerivation
{
  // We are Serializable, so we specify a version to allow changes to
  // method signatures without breaking serialization.  If you add or
  // remove fields, you should change this number to the current date.
  static final long serialVersionUID = 20020122L;

  // Variables starting with dkconfig_ should only be set via the
  // daikon.config.Configuration interface.
  /**
   * Boolean.  True iff SequenceFloatArbitrarySubsequence derived variables
   * should be generated.
   */
  public static boolean dkconfig_enabled = false;

  // base1 is the sequence
  // base2 is the starting index
  // base3 is the ending index
  public VarInfo seqvar() { return base1; }
  public VarInfo startvar() { return base2; }
  public VarInfo endvar() { return base3; }

  // True if the subsequence includes its left endpoint.
  public final boolean left_closed;

  // True if the subsequence includes its right endpoint.
  public final boolean right_closed;

  /**
   * Represents a subsequence of a sequence.  The subsequence a[i..j]
   * includes the endpoints i and j.  The subsequence is meaningful if:
   *  i &ge; 0
   *  i &le; a.length
   *  j &ge; -1
   *  j &le; a.length-1
   *  i &le; j+1
   * These are the empty array:
   *   a[0..-1]
   *   a[a.length..a.length-1]
   * These are illegal:
   *   a[1..-1]
   *   a[a.length..a.length-2]
   *   a[a.length+1..a.length]
   *
   * @param left_closed true means the range starts at i; false means
   * it starts at i+1
   * @param right_closed true means the range ends at j; false means
   * it ends at j-1.
   */
  public SequenceFloatArbitrarySubsequence(VarInfo vi1, VarInfo vi2, VarInfo vi3,
                                   boolean left_closed, boolean right_closed) {
    super(vi1, vi2, vi3);
    this.left_closed = left_closed;
    this.right_closed = right_closed;
  }

  public ValueAndModified computeValueAndModified(ValueTuple full_vt) {
    int mod1 = base1.getModified(full_vt);
    if (mod1 == ValueTuple.MISSING_NONSENSICAL) {
      return ValueAndModified.MISSING_NONSENSICAL;
    }
    int mod2 = base2.getModified(full_vt);
    if (mod2 == ValueTuple.MISSING_NONSENSICAL) {
      return ValueAndModified.MISSING_NONSENSICAL;
    }
    int mod3 = base2.getModified(full_vt);
    if (mod3 == ValueTuple.MISSING_NONSENSICAL) {
      return ValueAndModified.MISSING_NONSENSICAL;
    }

    Object val1 = base1.getValue(full_vt);
    if (val1 == null) {
      return ValueAndModified.MISSING_NONSENSICAL;
    }
    double[] val1_array = (double[]) val1;
    int val2 = base2.getIndexValue(full_vt);
    int val3 = base3.getIndexValue(full_vt);

    // One could argue that if the range exceeds the array bounds, one
    // should just return the whole array; but we don't do that.  We
    // say MISSING instead.

    // Note that the resulting array
    // is a[begin_inclusive..end_exclusive-1],
    // not a[begin_inclusive..end_exclusive].
    int begin_inclusive, end_exclusive;
    if (left_closed) {
      begin_inclusive = val2;
    } else {
      begin_inclusive = val2 + 1;
    }
    // begin_inclusive = val1_array.length is acceptable; that means the
    // empty array (given that end_exclusive is val1_arrayl.length)
    // (It is permitted to have a[a.length..a.length-1], which means
    // the empty array.  But a[MAX_INT..MAX_INT-1] is not meaningful.)
    if ((begin_inclusive < 0) || (begin_inclusive > val1_array.length)) {
      missing_array_bounds = true;
      return ValueAndModified.MISSING_NONSENSICAL;
    }

    if (right_closed) {
      end_exclusive = val3 + 1;
    } else {
      end_exclusive = val3;
    }
    // end_exclusive = 0 is acceptable; that means the empty array (given
    // that begin_inclusive is 0)
    if ((end_exclusive < 0) || (end_exclusive > val1_array.length)) {
      missing_array_bounds = true;
      return ValueAndModified.MISSING_NONSENSICAL;
    }

    if (end_exclusive - begin_inclusive < 0) {
      missing_array_bounds = true;
      return ValueAndModified.MISSING_NONSENSICAL;
    }

    int mod = (((mod1 == ValueTuple.UNMODIFIED)
                && (mod2 == ValueTuple.UNMODIFIED)
                && (mod3 == ValueTuple.UNMODIFIED))
               ? ValueTuple.UNMODIFIED
               : ValueTuple.MODIFIED);

    if ((begin_inclusive == 0) && (end_exclusive == val1_array.length)) {
      return new ValueAndModified(val1, mod);
    }

    double[] subarr = ArraysMDE.subarray(val1_array, begin_inclusive,
                                         end_exclusive - begin_inclusive);
    subarr = Intern.intern(subarr);
    return new ValueAndModified(subarr, mod);
  }

  protected VarInfo makeVarInfo() {

    return VarInfo.make_subsequence (seqvar(), startvar(),
                                     (left_closed ? 0 : 1),
                                     endvar(), (right_closed ? 0 : -1));
  }

    /** Returns the lower bound of the slice */
  public Quantify.Term get_lower_bound() {
    return new Quantify.VarPlusOffset (startvar(), (left_closed ? 0 : 1));
  }

  /** Returns the upper bound of the slice */
  public Quantify.Term get_upper_bound() {
    return new Quantify.VarPlusOffset (endvar(), (right_closed ? 0 : 1));
  }

  /** Returns the array variable for this slice */
  public VarInfo get_array_var() {
    return seqvar();
  }

  /*@Pure*/
  public boolean isSameFormula(Derivation other) {
    return (other instanceof SequenceFloatArbitrarySubsequence)
      && (((SequenceFloatArbitrarySubsequence)other).left_closed == this.left_closed)
      && (((SequenceFloatArbitrarySubsequence)other).right_closed == this.right_closed);
  }

  /** Returns the csharp name */
  /*@SideEffectFree*/
  public String csharp_name (String index) {
    String lower = get_lower_bound().csharp_name();
    String upper = get_upper_bound().csharp_name();
    // We do not need to check if seqvar().isPrestate() because it is redundant.
    return seqvar().csharp_name() + ".Slice(" + lower + ", " + upper + ")";
  }

  /** Returns the ESC name */
  /*@SideEffectFree*/
  public String esc_name(String index) {
    return String.format ("%s[%s..%s]", seqvar().esc_name(),
                    get_lower_bound().esc_name(), get_upper_bound().esc_name());
  }

}
