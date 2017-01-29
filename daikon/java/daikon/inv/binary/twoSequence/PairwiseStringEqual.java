// ***** This file is automatically generated from PairwiseIntComparison.java.jpp

package daikon.inv.binary.twoSequence;

import daikon.*;
import daikon.inv.*;
import daikon.inv.binary.twoScalar.*;
import daikon.derive.binary.*;
import daikon.suppress.*;
import daikon.Quantify.QuantFlags;

import plume.ArraysMDE;
import java.util.Iterator;
import java.util.logging.Logger;
import java.util.logging.Level;

/*>>>
import org.checkerframework.checker.interning.qual.*;
import org.checkerframework.checker.lock.qual.*;
import org.checkerframework.checker.nullness.qual.*;
import org.checkerframework.dataflow.qual.*;
import typequals.*;
*/

/**
 * Represents an invariant between corresponding elements of two
 * sequences of String values.  The length of the sequences must match for
 * the invariant to hold.  A comparison is made over each
 * <code>(x[i], y[i])</code> pair.
 * Thus, <code>x[0]</code> is compared to <code>y[0]</code>,
 * <code>x[1]</code> to <code>y[1]</code>, and so forth.
 * Prints as <code>x[] == y[]</code>.
 */

public class PairwiseStringEqual
  extends TwoSequenceString
{
  // We are Serializable, so we specify a version to allow changes to
  // method signatures without breaking serialization.  If you add or
  // remove fields, you should change this number to the current date.
  static final long serialVersionUID = 20030822L;

  /** Debug tracer. */
  public static final Logger debug =
    Logger.getLogger("daikon.inv.binary.twoSequence.PairwiseStringEqual");

  // Variables starting with dkconfig_ should only be set via the
  // daikon.config.Configuration interface.
  /**
   * Boolean.  True iff PairwiseIntComparison invariants should be considered.
   */
  public static boolean dkconfig_enabled = Invariant.invariantEnabledDefault;

  static final boolean debugPairwiseIntComparison = false;

  protected PairwiseStringEqual(PptSlice ppt) {
    super(ppt);
  }

  protected /*@Prototype*/ PairwiseStringEqual() {
    super();
  }

  private static /*@Prototype*/ PairwiseStringEqual proto = new /*@Prototype*/ PairwiseStringEqual ();

  /** Returns the prototype invariant for PairwiseStringEqual */
  public static /*@Prototype*/ PairwiseStringEqual get_proto() {
    return proto;
  }

  /** Returns whether or not this invariant is enabled */
  public boolean enabled() {
    return dkconfig_enabled;
  }

  /** PairwiseStringEqual is only valid on integral types */
  public boolean instantiate_ok (VarInfo[] vis) {

    if (!valid_types (vis)) {
      return false;
    }

    return true;
  }

  /** instantiates the invariant on the specified slice */
  protected PairwiseStringEqual instantiate_dyn (/*>>> @Prototype PairwiseStringEqual this,*/ PptSlice slice) {
    PairwiseStringEqual inv = new PairwiseStringEqual(slice);
    if (logOn()) {
      inv.log ("instantiate");
    }
    return inv;
  }

  /*@Pure*/
  public /*@Nullable*/ DiscardInfo isObviousStatically (VarInfo[] vis) {
    VarInfo var1 = vis[0];
    VarInfo var2 = vis[1];

    DiscardInfo di = null;
    di = SubSequence.isObviousSubSequence(this, var1, var2);
    if (di == null) {
      di = SubSequence.isObviousSubSequence(this, var2, var1);
    }
    if (di != null) {
      Global.implied_noninstantiated_invariants++;
      return di;
    }

    // Don't instantiate if the variables can't have order
    if (!var1.aux.hasOrder() ||
        !var2.aux.hasOrder()) {
      if (debug.isLoggable(Level.FINE)) {
        debug.fine ("Not instantitating for because order has no meaning: " +
                     var1.name() + " and " + var2.name());
      }
      return new DiscardInfo(this, DiscardCode.obvious, "Obvious statically since order has no meaning");
    }

    return super.isObviousStatically (vis);
  }

  /*@Pure*/
  public /*@Nullable*/ DiscardInfo isObviousDynamically (VarInfo[] vis) {
    DiscardInfo super_result = super.isObviousDynamically(vis);
    if (super_result != null) {
      return super_result;
    }

    // Subsequence invariants are implied by the same invariant over
    // the supersequence
    DiscardInfo di = superseq_implies (vis);
    if (di != null) {
      return di;
    }

    return null;
    }

  /**
   * Checks to see if the same invariant exists over supersequences of
   * these variables:
   *
   *    (A[] op B[]) ^ (i == j)  &rArr; A[i..] op B[j..]
   *    (A[] op B[]) ^ (i == j)  &rArr; A[..i] op B[..j]
   */
  private /*@Nullable*/ DiscardInfo superseq_implies (VarInfo[] vis) {

    // Make sure the variables are SequenceStringSubsequence with the same start/end
    VarInfo v1 = vis[0];
    VarInfo v2 = vis[1];
    if (!v1.isDerived() || !(v1.derived instanceof SequenceStringSubsequence)) {
      return null;
    }
    if (!v2.isDerived() || !(v2.derived instanceof SequenceStringSubsequence)) {
      return null;
    }
    @SuppressWarnings("nullness") // checker bug: flow
    /*@NonNull*/ SequenceStringSubsequence der1 = (SequenceStringSubsequence) v1.derived;
    @SuppressWarnings("nullness") // checker bug: flow
    /*@NonNull*/ SequenceStringSubsequence der2 = (SequenceStringSubsequence) v2.derived;
    if ((der1.from_start != der2.from_start)
        || (der1.index_shift != der2.index_shift))
      return null;

    // Make sure the subscripts are equal
    DiscardInfo di = new DiscardInfo (this, DiscardCode.obvious, "");
    if (!ppt.parent.check_implied_canonical (di, der1.sclvar(), der2.sclvar(),
                                             IntEqual.get_proto()))
      return null;

    // See if the super-sequences have the same invariant
    if (!ppt.parent.check_implied_canonical (di, der1.seqvar(), der2.seqvar(),
                                             PairwiseStringEqual.get_proto()))
      return null;

    // Add in the vis variables to di reason (if they are different)
    di.add_implied_vis (vis);
    return di;
  }

  protected Invariant resurrect_done_swapped() {

      return this;
  }

    /*@Pure*/
    public boolean is_symmetric() {
    return true;
  }

  public String repr(/*>>>@GuardSatisfied PairwiseStringEqual this*/) {
    return "PairwiseStringEqual" + varNames() + ": ";
  }

  public String getComparator() {
    return "==";
  }

  /*@SideEffectFree*/
  public String format_using(/*>>>@GuardSatisfied PairwiseStringEqual this,*/ OutputFormat format) {

    if (format.isJavaFamily()) return format_java_family(format);

    if (format == OutputFormat.DAIKON) return format_daikon();
    if (format == OutputFormat.ESCJAVA) return format_esc();
    if (format == OutputFormat.SIMPLIFY) return format_simplify();
    if (format == OutputFormat.CSHARPCONTRACT) return format_csharp();

    return format_unimplemented(format);
  }

  public String format_daikon(/*>>>@GuardSatisfied PairwiseStringEqual this*/) {
    return var1().name() + " == " + var2().name()
      + " (elementwise)";
  }

  public String format_esc(/*>>>@GuardSatisfied PairwiseStringEqual this*/) {
    String[] form = VarInfo.esc_quantify (var1(), var2());
    return form[0] + "(" + form[1] + " == " + form[2] + ")" + form[3];
  }

  public String format_simplify(/*>>>@GuardSatisfied PairwiseStringEqual this*/) {
    String[] form = VarInfo.simplify_quantify (QuantFlags.element_wise(),
                                               var1(), var2());
    return form[0] + "(EQ " + form[1] + " " + form[2] + ")" + form[3];
  }

  public String format_java_family(/*>>>@GuardSatisfied PairwiseStringEqual this,*/ OutputFormat format) {
    return "daikon.Quant.pairwiseEqual(" + var1().name_using(format)
      + ", " + var2().name_using(format) + ")";
  }

  public String format_csharp(/*>>>@GuardSatisfied PairwiseStringEqual this*/) {

    String[] split1 = var1().csharp_array_split();
    String[] split2 = var2().csharp_array_split();

    String equals_str;
    String end_str;

    equals_str = ".Equals(";
    end_str = ")";

    return "Contract.ForAll(0, " + split1[0] + ".Count(), i => " +  split1[0] + "[i]"  + split1[1] + equals_str + split2[0] + "[i]" + split2[1] + end_str + ")";
  }

  public InvariantStatus check_modified(String /*@Interned*/ [] a1, String /*@Interned*/ [] a2, int count) {
    assert a1 != null && a2 != null
      : var1() + " " + var2() + " " + FileIO.get_linenum();
    if (a1.length != a2.length || a1.length == 0 || a2.length == 0) {
      // destroyAndFlow();
      return InvariantStatus.FALSIFIED;
    }

    int len = a1.length;
    // int len = Math.min(a1.length, a2.length);

    for (int i=0; i<len; i++) {
      String v1 = a1[i];
      String v2 = a2[i];
      if (! (v1 != null && v2!= null && v1.equals( v2)) ) {
        //  destroyAndFlow();
        return InvariantStatus.FALSIFIED;
      }
    }
    return InvariantStatus.NO_CHANGE;
  }

    public InvariantStatus add_modified(String /*@Interned*/ [] a1, String /*@Interned*/ [] a2,
                                        int count) {
      if (logDetail()) {
        log (debug, "saw add_modified (" + ArraysMDE.toString(a1) +
             ", " + ArraysMDE.toString(a2) + ")");
      }
      return check_modified(a1, a2, count);
    }

  protected double computeConfidence() {
    // num_elt_values() would be more appropriate
    // int num_values = ((PptSlice2) ppt).num_elt_values();
    int num_values = ppt.num_samples();
    if (num_values == 0) {
      return Invariant.CONFIDENCE_UNJUSTIFIED;
    } else {

      // It's an equality invariant
      return Invariant.CONFIDENCE_JUSTIFIED;
    }
  }

  /*@Pure*/
  public boolean isSameFormula(Invariant other) {
    return true;
  }

  /*@Pure*/
  public boolean isExclusiveFormula(Invariant other) {
    return false;
  }

  // Look up a previously instantiated invariant.
  public static /*@Nullable*/ PairwiseStringEqual find(PptSlice ppt) {
    assert ppt.arity() == 2;
    for (Invariant inv : ppt.invs) {
      if (inv instanceof PairwiseStringEqual) {
        return (PairwiseStringEqual) inv;
      }
    }
    return null;
  }

  /**
   * Returns a list of non-instantiating suppressions for this invariant.
   */
  /*@Pure*/
  public /*@Nullable*/ NISuppressionSet get_ni_suppressions() {
    return suppressions;
  }

  /** Definition of this invariant (the suppressee) */
  private static NISuppressee suppressee
    = new NISuppressee (PairwiseStringEqual.class, 2);

  // Suppressor definitions (used in suppressions below)
  private static NISuppressor
    v1_eq_v2 = new NISuppressor (0, 1, PairwiseStringEqual.class),
    v1_gt_v2 = new NISuppressor (0, 1, PairwiseStringGreaterThan.class),
    v1_lt_v2 = new NISuppressor (0, 1, PairwiseStringLessThan.class);

    private static /*@Nullable*/ NISuppressionSet suppressions = null;

}
