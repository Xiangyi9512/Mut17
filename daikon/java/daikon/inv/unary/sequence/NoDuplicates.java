// ***** This file is automatically generated from NoDuplicates.java.jpp

package daikon.inv.unary.sequence;

import daikon.*;
import daikon.inv.*;
import daikon.inv.binary.twoSequence.*;
import daikon.inv.unary.scalar.*;
import daikon.suppress.*;
import daikon.derive.binary.SequencesPredicate;
import daikon.derive.binary.SequencesJoin;
import daikon.Quantify.QuantFlags;

import plume.*;

import java.util.*;
import java.util.logging.*;

/*>>>
import org.checkerframework.checker.interning.qual.*;
import org.checkerframework.checker.lock.qual.*;
import org.checkerframework.checker.nullness.qual.*;
import org.checkerframework.dataflow.qual.*;
import typequals.*;
*/

/**
 * Represents sequences of long that contain no duplicate elements.
 * Prints as <code>x[] contains no duplicates</code>.
 */

public class NoDuplicates
  extends SingleScalarSequence
{
  // We are Serializable, so we specify a version to allow changes to
  // method signatures without breaking serialization.  If you add or
  // remove fields, you should change this number to the current date.
  static final long serialVersionUID = 20040204L;

  // Variables starting with dkconfig_ should only be set via the
  // daikon.config.Configuration interface.
  /**
   * Boolean.  True iff NoDuplicates invariants should be considered.
   */
  public static boolean dkconfig_enabled = false;

  /** Debug tracer. */
  public static final Logger debug = Logger.getLogger("daikon.inv.unary.sequence.NoDuplicates");

  protected NoDuplicates(PptSlice ppt) {
    super(ppt);
  }

  protected /*@Prototype*/ NoDuplicates() {
    super();
  }

  private static /*@Prototype*/ NoDuplicates proto = new /*@Prototype*/ NoDuplicates ();

  /** Returns the prototype invariant for NoDuplicates */
  public static /*@Prototype*/ NoDuplicates get_proto() {
    return proto;
  }

  /** returns whether or not this invariant is enabled */
  public boolean enabled() {
    return dkconfig_enabled;
  }

  /** instantiate an invariant on the specified slice */
  public NoDuplicates instantiate_dyn (/*>>> @Prototype NoDuplicates this,*/ PptSlice slice) {
    return new NoDuplicates (slice);
  }

  public String repr(/*>>>@GuardSatisfied NoDuplicates this*/) {
    return "NoDuplicates" + varNames() + ": ";
  }

  /*@SideEffectFree*/
  public String format_using(/*>>>@GuardSatisfied NoDuplicates this,*/ OutputFormat format) {
    if (debug.isLoggable(Level.FINE)) {
      debug.fine (repr());
    }

    if (format == OutputFormat.DAIKON) {
      return var().name() + " contains no duplicates";
    }

    if (format == OutputFormat.SIMPLIFY) {
      return format_simplify();
    }

    if (format.isJavaFamily()) {
      return format_java_family(format);
    }

    if (format == OutputFormat.CSHARPCONTRACT) {
      return format_csharp_contract();
    }

    return format_unimplemented(format);
  }

  public String format_simplify(/*>>>@GuardSatisfied NoDuplicates this*/) {
    String[] form = VarInfo.simplify_quantify (QuantFlags.distinct(), var(),
                                               var());
    return form[0] + "(NEQ " + form[1] + " " + form[2] + ")"
      + form[3];
  }

  public String format_java_family(/*>>>@GuardSatisfied NoDuplicates this,*/ OutputFormat format) {
    return "daikon.Quant.noDups(" + var().name_using(format) + ")";
  }

  public String format_csharp_contract(/*>>>@GuardSatisfied NoDuplicates this*/) {
    String collection = var().csharp_collection_string();
    return collection + ".Distinct().Count() == " + collection + ".Count()";
  }

  public InvariantStatus check_modified(long /*@Interned*/ [] a, int count) {
    // if (logDetail())
    //   log ("sample " + ArraysMDE.toString (a));
    for (int i=1; i<a.length; i++) {
      for (int j = 0; j < i; j++) {
        if (((a[i]) == ( a[j]))) {
          return InvariantStatus.FALSIFIED;
        }
      }
    }
    return InvariantStatus.NO_CHANGE;
  }

  public InvariantStatus add_modified(long /*@Interned*/ [] a, int count) {
    InvariantStatus status = check_modified(a, count);
    if (debug.isLoggable(Level.FINE) && (status == InvariantStatus.FALSIFIED)) {
      debug.fine ("Destroying myself with: " + var().name());
      debug.fine (ArraysMDE.toString(a));
    }
    return status;
  }

  protected double computeConfidence() {
    // num_no_dup_values() would be more appropriate
    // return 1 - Math.pow(.9, ((PptSlice1) ppt).num_no_dup_values());
    return 1 - Math.pow(.9, ((PptSlice1) ppt).num_samples());
  }

  /*@Pure*/
  public /*@Nullable*/ DiscardInfo isObviousStatically (VarInfo[] vis) {
    if (!vis[0].aux.hasDuplicates()) {
      return new DiscardInfo(this, DiscardCode.obvious, "Obvious statically");
    }
    return super.isObviousStatically (vis);
  }

  /**
   * Checks to see if this is obvious over the specified variables
   * Implements the following checks:
   * <pre>
   *    (A[] subsequence B[]) ^ (B[] has nodups) &rArr; A[] has nodups
   *    (A[] sorted by &gt;) v (A[] sorted by &lt;)    &rArr; A[] has nodups
   * </pre>
   * JHP: The first check is not valid because we can't rely on transitive
   *      checks because of missing (if B[] is missing, A[] could have dups
   *      on those samples)
   */
  /*@Pure*/
  public /*@Nullable*/ DiscardInfo isObviousDynamically(VarInfo[] vis) {
    DiscardInfo super_result = super.isObviousDynamically(vis);
    if (super_result != null) {
      return super_result;
    }

    // If the maximum size of the array is <= 1, then this is
    // obvious
    ValueSet.ValueSetScalarArray vs = (ValueSet.ValueSetScalarArray) vis[0].get_value_set();
    if (vs.max_length() <= 1) {
      return new DiscardInfo (this, DiscardCode.obvious, "Size of " + vis[0]
                               + " is <= " + vs.max_length());
    }

    // For every other NoDuplicates at this program point, see if there is a
    // subsequence relationship between that array and this one.
    VarInfo v1 = vis[0];

    PptTopLevel parent = ppt.parent;

    for (Iterator<Invariant> itor = parent.invariants_iterator(); itor.hasNext(); ) {
      Invariant inv = itor.next();
      if ((inv instanceof NoDuplicates) && (inv != this) && inv.enoughSamples()) {
        VarInfo v2 = inv.ppt.var_infos[0];
        if (SubSequence.isObviousSubSequenceDynamically(this, v1, v2)) {
          // System.out.println("obvious: " + format() + "   because of " + inv.format());
          log ("Obvious- Obvious sub sequence with %s", inv.format());
          return new DiscardInfo(this, DiscardCode.obvious, "Invariant holds over a supersequence");
        }
      }
    }

    // If the sequence is sorted by < or >, then there are obviously no duplicates

    PptSlice slice = this.ppt.parent.findSlice_unordered (vis);
    if (slice != null) {
      for (Invariant inv : slice.invs) {
        if ((inv instanceof EltwiseIntLessThan)
            || (inv instanceof EltwiseFloatLessThan)
            || (inv instanceof EltwiseIntGreaterThan)
            || (inv instanceof EltwiseFloatGreaterThan)) {
          return new DiscardInfo(this, DiscardCode.obvious, "Sequence is sorted: " + inv.format());
        }
      }
    }

    return null;
  }

  /*@Pure*/
  public boolean isSameFormula(Invariant other) {
    assert other instanceof NoDuplicates;
    return true;
  }

}
