package demo.assess

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView

class DemoActivity : AppCompatActivity() {
  private val SAMPLE_TIMES: Int = 10

  private val FRAMELAYOUT_PAIR: Pair<String, Int> = Pair("FrameLayout", R.layout.layout_fl)
  private val LINEARLAYOUT_PAIR: Pair<String, Int> = Pair("LinearLayout", R.layout.layout_ll)
  private val RELATIVELAYOUT_PAIR: Pair<String, Int> = Pair("RelativeLayout", R.layout.layout_rl)
  private val PERCENTFRAMELAYOUT_PAIR: Pair<String, Int> = Pair("PercentFrameLayout",
      R.layout.layout_pfl)
  private val PERCENTRELATIVELAYOUT_PAIR: Pair<String, Int> = Pair("PercentRelativeLayout",
      R.layout.layout_prl)
  private val CONSTRAINTLAYOUT_PAIR: Pair<String, Int> = Pair("ConstraintLayout",
      R.layout.layout_cl)

  private val container by lazy { findViewById(R.id.container) as FrameLayout }
  private val resultTextView by lazy { findViewById(R.id.result_textview) as TextView }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_demo)

    // Dummy to avoid data fluctuations
    container.inflate(R.layout.layout_fl)
    container.removeAllViews()
    resultTextView.text = ""

    findViewById(R.id.inflate_framelayout_button).setOnClickListener { v ->
      assessLayout(mapOf(FRAMELAYOUT_PAIR))
    }

    findViewById(R.id.inflate_linearlayout_button).setOnClickListener { v ->
      assessLayout(mapOf(LINEARLAYOUT_PAIR))
    }

    findViewById(R.id.inflate_relativelayout_button).setOnClickListener { v ->
      assessLayout(mapOf(RELATIVELAYOUT_PAIR))
    }

    findViewById(R.id.inflate_percentframelayout_button).setOnClickListener { v ->
      assessLayout(mapOf(PERCENTFRAMELAYOUT_PAIR))
    }

    findViewById(R.id.inflate_percent_relativelayout_button).setOnClickListener { v ->
      assessLayout(mapOf(PERCENTRELATIVELAYOUT_PAIR))
    }

    findViewById(R.id.inflate_constraintlayout_button).setOnClickListener { v ->
      assessLayout(mapOf(CONSTRAINTLAYOUT_PAIR))
    }

    findViewById(R.id.assess_all_layouts_button).setOnClickListener { v ->
      assessLayout(
          mapOf(FRAMELAYOUT_PAIR, LINEARLAYOUT_PAIR, RELATIVELAYOUT_PAIR, PERCENTFRAMELAYOUT_PAIR,
              PERCENTRELATIVELAYOUT_PAIR, CONSTRAINTLAYOUT_PAIR))
    }
  }

  private fun assessLayout(map: Map<String, Int>) {
    val resultString: StringBuilder = StringBuilder()
    for ((i, s) in map) {
      val mutableList: MutableList<Long> = arrayListOf()
      for (x in 1..SAMPLE_TIMES) {
        mutableList.add(inflateAndMeasureLayout(s))
      }
      resultString.append(i)
      resultString.append(" - " + mutableList.average())
      resultString.append(" ms\n")
    }
    resultTextView.text = resultString.toString()
  }

  private fun inflateAndMeasureLayout(layoutRes: Int): Long {
    container.removeAllViews()
    val now: Long = System.currentTimeMillis()
    val inflatedView = container.inflate(layoutRes)
    container.addView(inflatedView)
    return System.currentTimeMillis() - now
  }

  fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
  }
}
