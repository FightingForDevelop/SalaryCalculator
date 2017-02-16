package com.xiaoxiao.salarycalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xiaoxiao.salarycalculator.utils.ToastUtils;

/**
 * v 1.0 :第一版主要是根据基本工资,按照北京纳税标准以及我们公司工资的计算标准
 * 工资标准:如果不是满勤就用减法,使用总工资-每天的工资(总工资/21.75)
 * <p>
 * 由于我目前月薪不高,所以现在最高基本工资是38500元,再高了小资就计算不了了
 * <p>
 * Created by zxx on 2017/2/16.
 */
public class MainActivity extends AppCompatActivity {

	EditText et_basic;//基本工资
	EditText et_insure;//五险一金
	EditText et_absence;//缺勤
	EditText et_overtime;//加班
	TextView tv_allRate;//总的个人所得税
	TextView tv_finalSalary;//最后所得工资

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		et_basic = (EditText) findViewById(R.id.et_basic);
		et_insure = (EditText) findViewById(R.id.et_insure);
		et_absence = (EditText) findViewById(R.id.et_absence);
		et_overtime = (EditText) findViewById(R.id.et_overtime);
		tv_allRate = (TextView) findViewById(R.id.tv_allRate);
		tv_finalSalary = (TextView) findViewById(R.id.tv_finalSalary);
	}

	public void calculator(View view) {
		String basic = et_basic.getText().toString().trim();
		String insure = et_insure.getText().toString().trim();
		String absence = et_absence.getText().toString().trim();
		String overtime = et_overtime.getText().toString().trim();
		float basicSalary = Float.parseFloat(basic);
		float insureGold = Float.parseFloat(insure);
		int absenceDay = Integer.parseInt(absence);//获取缺勤天数
		int overtimeDay = Integer.parseInt(overtime);//获取加班天数
		//计算应得工资
		//如果加班或缺勤,每天的工资为
		double perDay = basicSalary / 21.75;
		//计算总工资 = 基本工资 + 加班的工资 - 缺勤的工资
		double allSalary = basicSalary + overtimeDay * perDay - absenceDay * perDay;
		double youHave = allSalary - insureGold;
		if (youHave < 0) {
			ToastUtils.show("吃饭的钱都没有了,还上五险作甚!");
			return;
		}
		//需要纳税的工资 = 总工资 - 五险一金 - 免征额(3500)
		double payRate = youHave - 3500;
		//由于是个人纳税,所以使用含税级距的标准
		double allRate = 0;
		if (payRate > 0) {
			allRate = caculateRate(payRate);
		}
		//最后赢得工资为
		double finalSalary = youHave - allRate;
		//给控件赋值
		tv_allRate.setText(roundDouble(allRate) + "");
		tv_finalSalary.setText(roundDouble(finalSalary) + "");
	}

	/**
	 * 传入工资纳税的部分,获取总共需要交纳的个人所得税
	 * @param payRate 传入工资纳税的部分
	 * @return 总共需要交纳的个人所得税
	 */
	private double caculateRate(double payRate) {
		double allRate = 0;
		/**
		 * 标准:      含税级距    税率(%)
		 *          不超过1500       3
		 *          1500-4500       10
		 *          4500-9000       20
		 *          9000-35000      25
		 */
		if (payRate < 1500) {
			allRate = payRate * 0.03;
		} else if (payRate < 4500) {
			allRate = 45 + (payRate - 1500) * 0.1;
		} else if (payRate < 9000) {
			allRate = 45 + 300 + (payRate - 4500) * 0.2;
		} else if (payRate < 35000) {
			allRate = 45 + 300 + 900 + (payRate - 9000) * 0.1;
		}
		return allRate;
	}

	/**
	 * 小数 四舍五入 保留2位
	 *
	 * @param val 原始小数
	 * @return 四舍五入后保留的小数
	 */

	public static Double roundDouble(double val) {
		Double ret = null;
		try {
			double factor = Math.pow(10, 2);
			ret = Math.floor(val * factor + 0.5) / factor;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
}
