package com.borstvoeding.growthcurve.ref;

import java.util.Arrays;
import java.util.List;

import android.content.Context;

import com.borstvoeding.growthcurve.R;

public class GirlWeight implements Reference {
	private static final double[] refM2 = new double[] { 4821.2, 5067.0,
			5424.6, 5804.5, 6195.5, 6541.9, 6877.1, 7178.8, 7458.1, 7726.3,
			7972.1, 8206.7, 8441.3, 8653.6, 8854.7, 9055.9, 9245.8, 9424.6,
			9592.2, 9748.6, 9916.2, 10061.5, 10206.7, 10352.0, 10486.0,
			10620.1, 10754.2, 10877.1, 11000.0, 11122.9, 11234.6, 11346.4,
			11458.1, 11569.8, 11681.6, 11782.1, 11882.7, 11983.2, 12083.8,
			12184.4, 12273.7, 12374.3, 12463.7, 12553.1, 12642.5, 12731.8,
			12821.2, 12910.6, 13000.0, 13089.4, 13167.6, 13257.0, 13335.2,
			13424.6, 13502.8, 13592.2, 13670.4, 13748.6, 13838.0, 13916.2,
			13994.4, 14072.6, 14150.8, 14229.1, 14307.3, 14385.5, 14463.7,
			14541.9, 14620.1, 14698.3, 14776.5, 14854.7, 14933.0, 15011.2,
			15089.4, 15167.6, 15245.8, 15324.0, 15391.1, 15469.3, 15547.5,
			15625.7, 15703.9, 15782.1, 15860.3, 15938.5, 16016.8, 16095.0,
			16173.2, 16251.4, 16329.6, 16407.8, 16486.0, 16564.2, 16642.5,
			16720.7, 16798.9, 16877.1, 16955.3, 16977.7, 17055.9, 17134.1,
			17212.3, 17290.5, 17379.9, 17458.1, 17536.3, 17614.5, 17692.7,
			17770.9, 17860.3, 17938.5, 18016.8, 18095.0, 18173.2, 18262.6,
			18340.8, 18419.0, 18497.2, 18575.4, 18653.6, 18731.8, 18810.1,
			18899.4, 18977.7, 19055.9, 19134.1, 19212.3, 19290.5, 19368.7,
			19446.9, 19525.1, 19603.4, 19681.6, 19759.8, 19838.0, 19916.2,
			19994.4, 20083.8, 20162.0, 20240.2, 20318.4, 20396.6, 20474.9,
			20564.2, 20642.5, 20720.7, 20798.9, 20877.1, 20966.5, 21044.7,
			21122.9, 21212.3, 21290.5, 21368.7, 21458.1, 21536.3, 21625.7,
			21703.9, 21793.3, 21871.5, 21960.9, 22039.1, 22128.5, 22206.7,
			22296.1, 22385.5, 22463.7, 22553.1, 22642.5, 22720.7, 22810.1,
			22899.4, 22977.7, 23067.0, 23156.4, 23234.6, 23324.0, 23413.4,
			23502.8, 23581.0, 23670.4, 23759.8, 23849.2, 23927.4, 24016.8,
			24106.1, 24195.5, 24284.9, 24363.1, 24452.5, 24541.9, 24631.3,
			24720.7, 24798.9, 24888.3, 24977.7, 25067.0, 25156.4, 25245.8,
			25335.2, 25413.4, 25502.8, 25592.2, 25681.6, 25770.9, 25860.3,
			25949.7, 26039.1, 26128.5, 26217.9, 26307.3, 26385.5, 26474.9,
			26564.2, 26653.6, 26743.0, 26832.4, 26921.8, 27011.2, 27100.6,
			27189.9, 27279.3, 27368.7, 27458.1, 27547.5, 27636.9, 27726.3,
			27815.6, 27905.0, 27994.4, 28083.8, 28173.2, 28262.6, 28352.0,
			28441.3, 28530.7, 28608.9, 28698.3, 28787.7, 28877.1, 28966.5,
			29055.9, 29145.3, 29234.6, 29324.0, 29413.4, 29502.8, 29536.3 };
	private static final double[] refM1 = new double[] { 4217.9, 4441.3,
			4743.0, 5100.6, 5435.8, 5759.8, 6050.3, 6318.4, 6575.4, 6810.1,
			7033.5, 7245.8, 7446.9, 7636.9, 7826.8, 7994.4, 8162.0, 8318.4,
			8474.9, 8620.1, 8754.2, 8888.3, 9022.3, 9145.3, 9257.0, 9379.9,
			9491.6, 9592.2, 9703.9, 9804.5, 9905.0, 10005.6, 10095.0, 10195.5,
			10284.9, 10374.3, 10463.7, 10541.9, 10631.3, 10709.5, 10798.9,
			10877.1, 10955.3, 11033.5, 11111.7, 11189.9, 11257.0, 11335.2,
			11413.4, 11480.4, 11558.7, 11625.7, 11703.9, 11770.9, 11838.0,
			11916.2, 11983.2, 12050.3, 12117.3, 12184.4, 12251.4, 12329.6,
			12396.6, 12463.7, 12530.7, 12597.8, 12664.8, 12731.8, 12798.9,
			12865.9, 12933.0, 13000.0, 13067.0, 13134.1, 13201.1, 13268.2,
			13324.0, 13391.1, 13458.1, 13525.1, 13592.2, 13659.2, 13726.3,
			13793.3, 13860.3, 13927.4, 13994.4, 14061.5, 14128.5, 14195.5,
			14262.6, 14329.6, 14396.6, 14463.7, 14530.7, 14597.8, 14664.8,
			14731.8, 14798.9, 14821.2, 14877.1, 14955.3, 15022.3, 15089.4,
			15156.4, 15223.5, 15290.5, 15357.5, 15424.6, 15491.6, 15558.7,
			15625.7, 15703.9, 15770.9, 15838.0, 15905.0, 15972.1, 16039.1,
			16106.1, 16173.2, 16240.2, 16307.3, 16374.3, 16441.3, 16508.4,
			16575.4, 16631.3, 16698.3, 16765.4, 16832.4, 16899.4, 16966.5,
			17033.5, 17100.6, 17167.6, 17223.5, 17290.5, 17357.5, 17424.6,
			17491.6, 17558.7, 17625.7, 17692.7, 17748.6, 17815.6, 17882.7,
			17949.7, 18016.8, 18083.8, 18150.8, 18217.9, 18284.9, 18352.0,
			18419.0, 18486.0, 18553.1, 18620.1, 18687.2, 18754.2, 18821.2,
			18888.3, 18955.3, 19022.3, 19089.4, 19156.4, 19234.6, 19301.7,
			19368.7, 19435.8, 19502.8, 19569.8, 19636.9, 19703.9, 19782.1,
			19849.2, 19916.2, 19983.2, 20050.3, 20117.3, 20184.4, 20262.6,
			20329.6, 20396.6, 20463.7, 20530.7, 20597.8, 20664.8, 20743.0,
			20810.1, 20877.1, 20944.1, 21011.2, 21078.2, 21156.4, 21223.5,
			21290.5, 21357.5, 21424.6, 21491.6, 21569.8, 21636.9, 21703.9,
			21770.9, 21838.0, 21916.2, 21983.2, 22050.3, 22117.3, 22184.4,
			22262.6, 22329.6, 22396.6, 22463.7, 22530.7, 22608.9, 22676.0,
			22743.0, 22810.1, 22877.1, 22955.3, 23022.3, 23089.4, 23156.4,
			23223.5, 23301.7, 23368.7, 23435.8, 23502.8, 23569.8, 23648.0,
			23715.1, 23782.1, 23849.2, 23916.2, 23994.4, 24061.5, 24128.5,
			24195.5, 24262.6, 24329.6, 24396.6, 24474.9, 24541.9, 24608.9,
			24676.0, 24743.0, 24810.1, 24877.1, 24910.6 };
	private static final double[] ref0 = new double[] { 3201.1, 3346.4, 3603.4,
			3882.7, 4162.0, 4419.0, 4653.6, 4877.1, 5089.4, 5279.3, 5458.1,
			5625.7, 5793.3, 5949.7, 6095.0, 6240.2, 6374.3, 6497.2, 6620.1,
			6731.8, 6843.6, 6944.1, 7044.7, 7145.3, 7234.6, 7324.0, 7413.4,
			7491.6, 7581.0, 7659.2, 7737.4, 7815.6, 7882.7, 7960.9, 8027.9,
			8095.0, 8162.0, 8229.1, 8284.9, 8352.0, 8419.0, 8474.9, 8530.7,
			8597.8, 8653.6, 8709.5, 8765.4, 8821.2, 8877.1, 8933.0, 8988.8,
			9044.7, 9089.4, 9145.3, 9201.1, 9257.0, 9301.7, 9357.5, 9413.4,
			9469.3, 9514.0, 9569.8, 9614.5, 9670.4, 9726.3, 9770.9, 9826.8,
			9871.5, 9927.4, 9983.2, 10027.9, 10083.8, 10128.5, 10184.4,
			10229.1, 10284.9, 10329.6, 10385.5, 10430.2, 10486.0, 10530.7,
			10586.6, 10631.3, 10687.2, 10731.8, 10787.7, 10832.4, 10888.3,
			10933.0, 10988.8, 11033.5, 11089.4, 11145.3, 11189.9, 11245.8,
			11290.5, 11346.4, 11391.1, 11446.9, 11458.1, 11502.8, 11558.7,
			11603.4, 11659.2, 11703.9, 11759.8, 11815.6, 11860.3, 11916.2,
			11960.9, 12016.8, 12061.5, 12117.3, 12162.0, 12217.9, 12262.6,
			12307.3, 12363.1, 12407.8, 12463.7, 12508.4, 12553.1, 12608.9,
			12653.6, 12698.3, 12743.0, 12798.9, 12843.6, 12888.3, 12933.0,
			12988.8, 13033.5, 13078.2, 13122.9, 13167.6, 13212.3, 13268.2,
			13312.8, 13357.5, 13402.2, 13446.9, 13491.6, 13536.3, 13581.0,
			13636.9, 13681.6, 13726.3, 13770.9, 13815.6, 13860.3, 13905.0,
			13949.7, 13994.4, 14039.1, 14095.0, 14139.7, 14184.4, 14229.1,
			14273.7, 14318.4, 14363.1, 14407.8, 14452.5, 14497.2, 14541.9,
			14586.6, 14631.3, 14687.2, 14731.8, 14776.5, 14821.2, 14865.9,
			14910.6, 14955.3, 15000.0, 15044.7, 15089.4, 15134.1, 15178.8,
			15223.5, 15268.2, 15312.8, 15357.5, 15402.2, 15446.9, 15491.6,
			15536.3, 15581.0, 15625.7, 15670.4, 15715.1, 15759.8, 15804.5,
			15849.2, 15893.9, 15927.4, 15972.1, 16016.8, 16061.5, 16106.1,
			16150.8, 16195.5, 16240.2, 16284.9, 16329.6, 16374.3, 16419.0,
			16463.7, 16508.4, 16553.1, 16597.8, 16642.5, 16676.0, 16720.7,
			16765.4, 16810.1, 16854.7, 16899.4, 16944.1, 16988.8, 17033.5,
			17078.2, 17122.9, 17167.6, 17212.3, 17257.0, 17290.5, 17335.2,
			17379.9, 17424.6, 17469.3, 17514.0, 17558.7, 17603.4, 17648.0,
			17681.6, 17726.3, 17770.9, 17815.6, 17860.3, 17905.0, 17949.7,
			17983.2, 18027.9, 18072.6, 18117.3, 18162.0, 18195.5, 18206.7 };
	private static final double[] ref1 = new double[] { 2352.0, 2474.9, 2676.0,
			2910.6, 3134.1, 3346.4, 3547.5, 3737.4, 3905.0, 4061.5, 4217.9,
			4363.1, 4497.2, 4620.1, 4743.0, 4854.7, 4966.5, 5067.0, 5167.6,
			5268.2, 5357.5, 5435.8, 5525.1, 5603.4, 5681.6, 5748.6, 5826.8,
			5893.9, 5960.9, 6016.8, 6083.8, 6139.7, 6206.7, 6262.6, 6318.4,
			6374.3, 6419.0, 6474.9, 6519.6, 6575.4, 6620.1, 6664.8, 6720.7,
			6765.4, 6810.1, 6854.7, 6899.4, 6944.1, 6988.8, 7033.5, 7078.2,
			7111.7, 7156.4, 7201.1, 7245.8, 7279.3, 7324.0, 7368.7, 7413.4,
			7446.9, 7491.6, 7536.3, 7569.8, 7614.5, 7659.2, 7692.7, 7737.4,
			7782.1, 7815.6, 7860.3, 7905.0, 7938.5, 7983.2, 8016.8, 8061.5,
			8106.1, 8139.7, 8184.4, 8217.9, 8262.6, 8307.3, 8340.8, 8385.5,
			8419.0, 8463.7, 8497.2, 8541.9, 8575.4, 8620.1, 8653.6, 8698.3,
			8731.8, 8776.5, 8810.1, 8854.7, 8888.3, 8933.0, 8966.5, 9011.2,
			9022.3, 9055.9, 9100.6, 9134.1, 9178.8, 9212.3, 9257.0, 9290.5,
			9335.2, 9368.7, 9413.4, 9446.9, 9491.6, 9525.1, 9558.7, 9603.4,
			9636.9, 9681.6, 9715.1, 9748.6, 9782.1, 9826.8, 9860.3, 9893.9,
			9927.4, 9972.1, 10005.6, 10039.1, 10072.6, 10106.1, 10139.7,
			10184.4, 10217.9, 10251.4, 10284.9, 10318.4, 10352.0, 10385.5,
			10419.0, 10452.5, 10486.0, 10519.6, 10553.1, 10586.6, 10620.1,
			10653.6, 10687.2, 10720.7, 10754.2, 10787.7, 10810.1, 10843.6,
			10877.1, 10910.6, 10944.1, 10977.7, 11011.2, 11044.7, 11078.2,
			11100.6, 11134.1, 11167.6, 11201.1, 11234.6, 11268.2, 11290.5,
			11324.0, 11357.5, 11391.1, 11424.6, 11446.9, 11480.4, 11514.0,
			11547.5, 11569.8, 11603.4, 11636.9, 11670.4, 11692.7, 11726.3,
			11759.8, 11782.1, 11815.6, 11849.2, 11882.7, 11905.0, 11938.5,
			11972.1, 11994.4, 12027.9, 12061.5, 12083.8, 12117.3, 12139.7,
			12173.2, 12206.7, 12229.1, 12262.6, 12296.1, 12318.4, 12352.0,
			12374.3, 12407.8, 12441.3, 12463.7, 12497.2, 12530.7, 12553.1,
			12586.6, 12620.1, 12642.5, 12676.0, 12698.3, 12731.8, 12765.4,
			12787.7, 12821.2, 12843.6, 12877.1, 12910.6, 12933.0, 12966.5,
			12988.8, 13022.3, 13055.9, 13078.2, 13111.7, 13134.1, 13167.6,
			13189.9, 13223.5, 13257.0, 13279.3, 13312.8, 13335.2, 13368.7,
			13391.1, 13424.6, 13446.9, 13480.4, 13502.8, 13536.3, 13569.8,
			13592.2, 13625.7, 13648.0, 13681.6, 13703.9, 13737.4, 13715.1 };
	private static final double[] ref2 = new double[] { 1994.4, 2106.1, 2296.1,
			2497.2, 2709.5, 2910.6, 3089.4, 3257.0, 3413.4, 3558.7, 3703.9,
			3826.8, 3949.7, 4072.6, 4184.4, 4284.9, 4385.5, 4486.0, 4575.4,
			4653.6, 4743.0, 4821.2, 4899.4, 4966.5, 5044.7, 5111.7, 5167.6,
			5234.6, 5290.5, 5357.5, 5413.4, 5469.3, 5514.0, 5569.8, 5614.5,
			5670.4, 5715.1, 5759.8, 5804.5, 5849.2, 5893.9, 5938.5, 5983.2,
			6027.9, 6061.5, 6106.1, 6139.7, 6184.4, 6229.1, 6262.6, 6307.3,
			6340.8, 6374.3, 6419.0, 6452.5, 6497.2, 6530.7, 6564.2, 6608.9,
			6642.5, 6676.0, 6720.7, 6754.2, 6787.7, 6832.4, 6865.9, 6899.4,
			6944.1, 6977.7, 7011.2, 7044.7, 7089.4, 7122.9, 7156.4, 7189.9,
			7234.6, 7268.2, 7301.7, 7335.2, 7379.9, 7413.4, 7446.9, 7480.4,
			7514.0, 7547.5, 7592.2, 7625.7, 7659.2, 7692.7, 7726.3, 7759.8,
			7804.5, 7838.0, 7871.5, 7905.0, 7938.5, 7972.1, 8005.6, 8039.1,
			8050.3, 8083.8, 8117.3, 8162.0, 8195.5, 8229.1, 8262.6, 8296.1,
			8329.6, 8363.1, 8396.6, 8430.2, 8463.7, 8497.2, 8530.7, 8564.2,
			8597.8, 8631.3, 8664.8, 8698.3, 8731.8, 8765.4, 8798.9, 8821.2,
			8854.7, 8888.3, 8921.8, 8955.3, 8977.7, 9011.2, 9044.7, 9067.0,
			9100.6, 9134.1, 9167.6, 9189.9, 9223.5, 9245.8, 9279.3, 9312.8,
			9335.2, 9368.7, 9391.1, 9424.6, 9446.9, 9480.4, 9514.0, 9536.3,
			9569.8, 9592.2, 9614.5, 9648.0, 9681.6, 9703.9, 9726.3, 9759.8,
			9782.1, 9815.6, 9838.0, 9871.5, 9893.9, 9916.2, 9949.7, 9972.1,
			10005.6, 10027.9, 10050.3, 10083.8, 10106.1, 10128.5, 10162.0,
			10184.4, 10206.7, 10240.2, 10262.6, 10284.9, 10318.4, 10340.8,
			10363.1, 10385.5, 10419.0, 10441.3, 10463.7, 10486.0, 10519.6,
			10541.9, 10564.2, 10586.6, 10608.9, 10642.5, 10664.8, 10687.2,
			10709.5, 10731.8, 10765.4, 10787.7, 10810.1, 10832.4, 10854.7,
			10877.1, 10910.6, 10933.0, 10955.3, 10977.7, 11000.0, 11022.3,
			11055.9, 11078.2, 11100.6, 11122.9, 11145.3, 11167.6, 11201.1,
			11223.5, 11245.8, 11268.2, 11290.5, 11312.8, 11335.2, 11368.7,
			11391.1, 11413.4, 11435.8, 11458.1, 11480.4, 11502.8, 11536.3,
			11558.7, 11581.0, 11603.4, 11625.7, 11648.0, 11670.4, 11692.7,
			11726.3, 11748.6, 11770.9, 11793.3, 11815.6, 11838.0, 11860.3,
			11882.7, 11905.0, 11927.4, 11949.7, 11972.1, 11994.4, 12027.9,
			12050.3, 12027.9 };

	private static final List<double[]> values = Arrays.asList(//
			GirlWeight.refM2, //
			GirlWeight.refM1, //
			GirlWeight.ref0, //
			GirlWeight.ref1, //
			GirlWeight.ref2);

	private final Context context;

	public GirlWeight(Context context) {
		this.context = context;
	}

	@Override
	public String getChartTitle() {
		return context.getString(R.string.chartWeightTitle);
	}

	@Override
	public String getChartYTitle() {
		return context.getString(R.string.chartWeightYTitle);
	}

	@Override
	public List<double[]> getValues() {
		return values;
	}
}
