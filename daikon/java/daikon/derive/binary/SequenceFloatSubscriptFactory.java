// ***** This file is automatically generated from SequenceSubscriptFactory.java.jpp

package daikon.derive.binary;

import daikon.*;
import daikon.inv.binary.twoScalar.*; // for IntComparison
import daikon.inv.unary.scalar.*; // for LowerBound

import plume.*;
import java.util.*;

/*>>>
import org.checkerframework.checker.nullness.qual.*;
*/

// This controls derivations which use the scalar as an index into the
// sequence, such as getting the element at that index or a subsequence up
// to that index.

public final class SequenceFloatSubscriptFactory extends BinaryDerivationFactory {
  static boolean debug_set = false;
  static boolean debug_class_match = false;

  // When calling/creating the derivations, arrange that:
  //   base1 is the sequence
  //   base2 is the scalar

  public BinaryDerivation /*@Nullable*/ [] instantiate(VarInfo vi1, VarInfo vi2) {

    Debug debug = null;
    if (!debug_set) {
      debug_class_match = Debug.class_match (getClass());
      debug_set = true;
    }
    if (debug_class_match && Debug.logOn()) {
      debug = Debug.newDebug (getClass(), vi1.ppt, Debug.vis (vi1, vi2));
    }
    if (debug != null) {
      debug.log ("Considering Sequence Subscript Derivation");
    }

    // check if the derivations are globally disabled
    boolean enable_subscript = SequenceFloatSubscript.dkconfig_enabled;
    boolean enable_subsequence = SequenceFloatSubsequence.dkconfig_enabled;
    if (!enable_subscript && !enable_subsequence) {
      if (debug != null) debug.log ("Not Enabled");
      return null;
    }

    // This is not the very most efficient way to do this, but at least it is
    // comprehensible.
    VarInfo seqvar;
    VarInfo sclvar;

    if ((vi1.rep_type == ProglangType.DOUBLE_ARRAY)
        && (vi2.rep_type == ProglangType.DOUBLE)) {
      seqvar = vi1;
      sclvar = vi2;
    } else if ((vi2.rep_type == ProglangType.DOUBLE_ARRAY)
               && (vi1.rep_type == ProglangType.DOUBLE)) {
      seqvar = vi2;
      sclvar = vi1;
    } else {
      if (debug != null) debug.log ("Not Array/Int combination");
      return null;
    }

    if (!seqvar.aux.hasOrder()) {
      // Indexing doesn't make sense if order doesn't matter
      if (debug != null) debug.log ("Not ordered");
      return null;
    }

    if (! seqvar.indexCompatible(sclvar)) {
      if (debug != null) debug.log ("variables are not comparable");
      return null;
    }

    // For now, do nothing if the sequence is itself derived.
    if (seqvar.derived != null) {
      if (debug != null) debug.log ("Sequence variable derived");
      return null;
    }
    // For now, do nothing if the scalar is itself derived.
    if (sclvar.derived != null) {
      if (debug != null) debug.log ("Scalar variable derived");
      return null;
    }

    VarInfo seqsize = seqvar.sequenceSize();
    // System.out.println("BinaryDerivation.instantiate: sclvar=" + sclvar.name
    //                    + ", sclvar_rep=" + sclvar.canonicalRep().name
    //                    + ", seqsize=" + seqsize.name
    //                    + ", seqsize_rep=" + seqsize.canonicalRep().name);

    // SUPPRESS DERIVED VARIABLE: a[i] where i == a.length
    // SUPPRESS DERIVED VARIABLE: a[i-1] where i == a.length
    // SUPPRESS DERIVED VARIABLE: a[..i] where i == a.length
    // SUPPRESS DERIVED VARIABLE: a[..i-1] where i == a.length
    // SUPPRESS DERIVED VARIABLE: a[i..] where i == a.length
    // SUPPRESS DERIVED VARIABLE: a[i+1..] where i == a.length
    // Since both are canonical, this is equivalent to
    // "if (sclvar.canonicalRep() == seqsize.canonicalRep()) ..."
    if (sclvar == seqsize) {
      // a[len] a[len-1] a[0..len] a[0..len-1] a[len..] a[len+1..]
      Global.tautological_suppressed_derived_variables += 6;
      if (debug != null) debug.log ("sclvar related to length");
      return null;
    }

    // SUPPRESS DERIVED VARIABLE: a[i] where (i >= a.length) can be true
    // SUPPRESS DERIVED VARIABLE: a[i-1] where (i > a.length) can be true
    // SUPPRESS DERIVED VARIABLE: a[..i] where (i >= a.length) can be true
    // SUPPRESS DERIVED VARIABLE: a[..i-1] where (i > a.length) can be true
    // SUPPRESS DERIVED VARIABLE: a[i..] where (i > a.length) can be true
    // SUPPRESS DERIVED VARIABLE: a[i+1..] where (i >= a.length) can be true
    // ***** This eliminates the derivation if it can *ever* be
    // nonsensical/missing.  Is that what I want?
    // Find an IntComparison relationship over the scalar and the sequence
    // size, if possible.
    if (seqsize != null) {
      assert sclvar.ppt == seqsize.ppt;
      PptSlice compar_slice = sclvar.ppt.findSlice_unordered(sclvar, seqsize);
      if (compar_slice != null) {
        if ((sclvar.varinfo_index < seqsize.varinfo_index)
            ? FloatLessEqual.find(compar_slice) == null // sclvar can be more than seqsize
            : FloatGreaterEqual.find(compar_slice) == null // seqsize can be less than sclvar
            ) {
          Global.nonsensical_suppressed_derived_variables += 6;
          if (debug != null) debug.log ("sclvar is sometimes > length sequence");
          return null;
        } else if (FloatEqual.find(compar_slice) != null) {
          Global.nonsensical_suppressed_derived_variables += 3;
          ArrayList<BinaryDerivation> result = new ArrayList<BinaryDerivation>();
          if (enable_subscript) {
            result.add(new SequenceFloatSubscript(seqvar, sclvar, true)); // a[i-1]
          }
          if (enable_subsequence) {
            result.add(new SequenceFloatSubsequence(seqvar, sclvar, true, true)); // a[..i-1]
            result.add(new SequenceFloatSubsequence(seqvar, sclvar, false, false)); // a[i..]
          }
          if (debug != null) debug.log ("Found derivations: " + result);
          return result.toArray(new BinaryDerivation[result.size()]);
        }
      }
    }

    // Abstract out these next two.

    // If the scalar is a constant, then do all the following checks:
    //
    // If the scalar is a constant < 0:
    //   all derived variables are nonsensical
    // SUPPRESS DERIVED VARIABLE: a[i] where i<0 and i is constant
    // SUPPRESS DERIVED VARIABLE: a[i-1] where i<0 and i is constant
    // SUPPRESS DERIVED VARIABLE: a[..i] where i<0 and i is constant
    // SUPPRESS DERIVED VARIABLE: a[..i-1] where i<0 and i is constant
    // SUPPRESS DERIVED VARIABLE: a[i..] where i<0 and i is constant
    // SUPPRESS DERIVED VARIABLE: a[i+1..] where i<0 and i is constant
    // If the scalar is the constant 0:
    //   array[0] is already extracted
    //   array[-1] is nonsensical
    //   array[0..0] is already extracted
    //   array[0..-1] is nonsensical
    //   array[0..] is the same as array[]
    //   array[1..] should be extracted
    // SUPPRESS DERIVED VARIABLE: a[i] where i==0
    // SUPPRESS DERIVED VARIABLE: a[i-1] where i==0
    // SUPPRESS DERIVED VARIABLE: a[..i] where i==0
    // SUPPRESS DERIVED VARIABLE: a[..i-1] where i==0
    // SUPPRESS DERIVED VARIABLE: a[i..] where i==0
    // If the scalar is the constant 1:
    //   array[1] is already extracted
    //   array[0] is already extracted
    //   array[0..1] should be extracted
    //   array[0..0] is already extracted
    //   array[1..] should be extracted
    //   array[2..] should be extracted
    // SUPPRESS DERIVED VARIABLE: a[i] where i==1
    // SUPPRESS DERIVED VARIABLE: a[i-1] where i==1
    // SUPPRESS DERIVED VARIABLE: a[..i] where i==1
    // SUPPRESS DERIVED VARIABLE: a[..i-1] where i==1

    // Commented out:  seems to be eliminating desired invariants.
    if (false) {
    // If the lower bound for the variable is less than 0 (that is, if the
    // putative index can ever be negative), then suppress most of the
    // derived variables.
    // SUPPRESS DERIVED VARIABLE: a[i] where i<0 can be true
    // SUPPRESS DERIVED VARIABLE: a[i-1] where i<1 can be true
    // SUPPRESS DERIVED VARIABLE: a[..i] where i<-1 can be true
    // SUPPRESS DERIVED VARIABLE: a[..i-1] where i<0 can be true
    // SUPPRESS DERIVED VARIABLE: a[i..] where i<0 can be true
    // SUPPRESS DERIVED VARIABLE: a[i+1..] where i<-1 can be true
    PptSlice unary_slice = sclvar.ppt.findSlice(sclvar);
    if (unary_slice != null) {
      LowerBound lb_inv = LowerBound.find(unary_slice);
      if (lb_inv != null) {
        long lower_bound = lb_inv.min();
        if (lower_bound < -1) {
          Global.nonsensical_suppressed_derived_variables += 6;
          return null;
        } else if (lower_bound == -1) {
          Global.nonsensical_suppressed_derived_variables += 5;
          return (enable_subsequence) ? new BinaryDerivation[] {
            new SequenceFloatSubsequence(seqvar, sclvar, true, false), // a[..i]
            new SequenceFloatSubsequence(seqvar, sclvar, false, true), // a[i+1..]
          } : null;
        } else if (lower_bound == 0) {
          Global.nonsensical_suppressed_derived_variables += 1;
          ArrayList<BinaryDerivation> result = new ArrayList<BinaryDerivation>();
          if (enable_subscript) {
            result.add(new SequenceFloatSubscript(seqvar, sclvar, false)); // a[i]
          }
          if (enable_subsequence) {
            result.add(new SequenceFloatSubsequence(seqvar, sclvar, false, false)); // a[i..]
            result.add(new SequenceFloatSubsequence(seqvar, sclvar, false, true)); // a[i+1..]
            result.add(new SequenceFloatSubsequence(seqvar, sclvar, true, false)); // a[..i]
            result.add(new SequenceFloatSubsequence(seqvar, sclvar, true, true)); // a[..i-1]
          }
          return result.toArray(new BinaryDerivation[result.size()]);
        }
      }
    }
    }

    // If, for some canonical j, j=index+1, don't create array[index+1..].
    // If j=index-1, then don't create array[index-1] or array[0..index-1].
    // (j can have higher or lower VarInfo index than i.)
    boolean suppress_minus_1 = false;
    boolean suppress_plus_1 = false;

    // This ought to be abstracted out, maybe
    {
      assert sclvar.ppt == seqvar.ppt;
      Vector<LinearBinaryFloat> lbs = LinearBinaryFloat.findAll(sclvar);
      // System.out.println("For " + sclvar.name + ", " + lbs.size() + " LinearBinary invariants");
      for (Object lbObject : lbs) {
        LinearBinaryFloat lb = (LinearBinaryFloat) lbObject;
        // lb asserts that lb.var2() = lb.core.a * lb.var1() + lb.core.b <-- old
        // Note that var2 comes before var1; this makes the most sense
        // if you think of them as "y" and "x". <-- old

        // lb asserts that lb.core.a * lb.var1() + lb.core.b * lb.var2() + lb.core.c == 0
        // or lb.var2() == - (lb.core.a / lb.core.b) * lb.var1() - lb.core.c / lb.core.b

//        if (lb.core.a == 1) {
        if (-lb.core.a/lb.core.b == 1) {
          // Don't set unconditionally, and don't break:  we want to check
          // other variables as well.
//          if (lb.core.b == -1) {
          if (-lb.core.c/lb.core.b == -1) {
            if (lb.var1() == sclvar) {
              // j = index - 1
              suppress_minus_1 = true;
            } else {
              // index = j - 1, so j = index + 1
              suppress_plus_1 = true;
            }
          }
//          if (lb.core.b == 1) {
          if (-lb.core.c/lb.core.b == -1) {
            if (lb.var1() == sclvar) {
              // j = index + 1
              suppress_plus_1 = true;
            } else {
              // index = j + 1, so j = index - 1
              suppress_minus_1 = true;
            }
          }
          // System.out.println("For " + sclvar.name + " suppression: "
          //                    + "minus=" + suppress_minus_1
          //                    + " plus=" + suppress_plus_1
          //                    + " because of " + lb.format());
        }
      }
    }

    if (suppress_minus_1) {
      Global.tautological_suppressed_derived_variables += 2;
    }
    if (suppress_plus_1) {
      Global.tautological_suppressed_derived_variables += 1;
    }

    // End of applicability tests; now actually create the invariants

    Vector<BinaryDerivation> result = new Vector<BinaryDerivation>(6);
    if (enable_subscript) {
      // a[i]
      result.add(new SequenceFloatSubscript(seqvar, sclvar, false));
      // a[i-1]
      if (! suppress_minus_1) {
        result.add(new SequenceFloatSubscript(seqvar, sclvar, true));
      }
    }
    if (enable_subsequence) {
      // a[i..]
      result.add(new SequenceFloatSubsequence(seqvar, sclvar, false, false));
      // a[i+1..]
      if (! suppress_plus_1) {
        result.add(new SequenceFloatSubsequence(seqvar, sclvar, false, true));
      }
      // a[..i]
      result.add(new SequenceFloatSubsequence(seqvar, sclvar, true, false));
      // a[..i-1]
      if (! suppress_minus_1) {
        result.add(new SequenceFloatSubsequence(seqvar, sclvar, true, true));
      }
    }
    if (debug != null) debug.log ("Found derivations " + result);
    return result.toArray(new BinaryDerivation[0]);
  }

}
