<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

	<div class="base_title">
		<em></em>
		<strong>全国中小学学籍管理系统学生基本信息表</strong>
		<strong style="float:right;color:red">
			申请状态：{@if id==0}未填表
			{@else if statusStr=="SUBMIT_NONE"}未提交
			{@else if statusStr=="SUBMIT_ONCE"}已提交
			{@/if}
		</strong>
	</div>
	<div id="div_stuInfo_iptHint" style="display:none;"><!-- 学校拟录取您的孩子为2017级新生， -->
		<strong style="color:red">尊敬的家长：您好！为了顺利注册孩子的学籍，请您拿出孩子的户口，对照户口填写以下72项信息，填完后认真核对，确认无误后再提交。一旦提交，家长无法自行修改。如需修改，请前往学校提交更正申请，并附上户口复印件才能修改！</strong>
	</div>
	<form id="form_stuInfo" class="bs-docs-example">
	<div id="div_stuInfo_cover" style="background-color: rgba(255, 255, 255, 0);position: absolute;z-index: 999;"></div>
	<input id="ipt_id" name="id" value="$J{id}" style="width: 174px;" type="hidden" />
	<input id="ipt_stuId" name="stuId" value="$J{stuId}" style="width: 174px;" type="hidden" />
	<table class="table table-bordered table-striped">
		<thead>
			<tr>
				<th width="20%" colspan="2">学校名称</th>
				<th width="30%">华中师范大学附属小学</th>
				<th width="20%" colspan="2">学校标识码</th>
				<th width="30%">2142001636</th>
			</tr>
			<tr>
				<th width="5%">编号</th>
				<th width="15%">项目名称</th>
				<th width="30%">基础数据</th>
				<th width="5%">编号</th>
				<th width="15%">项目名称</th>
				<th width="30%">基础数据</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td colspan="6"><strong>学生个人基础信息</strong></td>
			</tr>
			<tr>
				<td>1</td>
				<td>姓名</td>
				<td>$J{stuApply.name}</td>
				<td>8</td>
				<td>身份证件类型</td>
				<td>身份证</td>
			</tr>
			<tr>
				<td>2</td>
				<td>性别</td>
				<td>{@if stuApply.sexStr=="TRUE"}男{@else}女{@/if}</td>
				<td>9</td>
				<td>身份证件号</td>
				<td>$J{stuApply.cardNo}</td>
			</tr>
			<tr>
				<td>3</td>
				<td>出生日期</td>
				<td>$J{stuApply.dateOfBirthStr}</td>
				<td>10</td>
				<td>港澳台外侨</td>
				<td>$J{stuApply.gatqw}</td>
			</tr>
			<tr>
				<td>4</td>
				<td>出生地</td>
				<td><input id="ipt_addressOfBirth" name="addressOfBirth" value="$J{addressOfBirth}" style="width: 95%;" type="text" class="inp" placeholder="请下载“行政区划代码查询表”并参照该表输入相应值!" /></td>
				<td>11</td>
				<td>政治面貌</td>
				<td>群众</td>
			</tr>
			<tr>
				<td>5</td>
				<td>籍贯</td>
				<td>$J{stuApply.birthplace}</td>
				<td>12</td>
				<td>健康状况</td>
				<td>$J{stuApply.jkzt}</td>
			</tr>
			<tr>
				<td>6</td>
				<td>民族</td>
				<td>$J{stuApply.nation}</td>
				<td>13</td>
				<td>照片</td>
				<td>入学后由学校统一采集</td>
			</tr>
			<tr>
				<td>7</td>
				<td>国籍/地区</td>
				<td>$J{stuApply.citizenship}</td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td colspan="6"><strong>学生个人辅助信息</strong></td>
			</tr>
			<tr>
				<td>14</td>
				<td>姓名拼音</td>
				<td><input id="ipt_other1" name="other1" value="$J{other1}" style="width: 95%;" type="text" class="inp" /></td>
				<td>17</td>
				<td>户口所在地</td>
				<td><input id="ipt_other4" name="other4" value="$J{other4}" style="width: 95%;" type="text" class="inp" placeholder="请下载“行政区划代码查询表”并参照该表输入相应值!" /></td>
			</tr>
			<tr>
				<td>15</td>
				<td>曾用名</td>
				<td><input id="ipt_other2" name="other2" value="$J{other2}" style="width: 95%;" type="text" class="inp" /></td>
				<td>18</td>
				<td>户口性质</td>
				<td>$J{stuApply.other4}<input name="other5" value="$J{stuApply.other4}" style="width: 95%;" type="hidden" class="inp" /></td>
			</tr>
			<tr>
				<td>16</td>
				<td>身份证件有效期</td>
				<td><input id="ipt_other3" name="other3" value="$J{other3}" style="width: 95%;" type="text" class="inp" /></td>
				<td>19</td>
				<td>特长</td>
				<td><input id="ipt_other6" name="other6" value="$J{other6}" style="width: 95%;" type="text" class="inp" maxlength="10" placeholder="10字以内" /></td>
			</tr>
			<tr>
				<td colspan="6"><strong>学生籍贯基本信息</strong></td>
			</tr>
			<tr>
				<td>20</td>
				<td>学籍辅号</td>
				<td>$J{other7}</td>
				<td>24</td>
				<td>入学年月</td>
				<td>$J{other11}</td>
			</tr>
			<tr>
				<td>21</td>
				<td>班内学号</td>
				<td>$J{other8}</td>
				<td>25</td>
				<td>入学方式</td>
				<td>$J{other12}</td>
			</tr>
			<tr>
				<td>22</td>
				<td>年级</td>
				<td>$J{other9}</td>
				<td>26</td>
				<td>就读方式</td>
				<td>$J{other13}</td>
			</tr>
			<tr>
				<td>23</td>
				<td>班级</td>
				<td>$J{other10}</td>
				<td>27</td>
				<td>学生来源</td>
				<td>$J{other14}</td>
			</tr>
			<tr>
				<td colspan="6"><strong>学生个人联系信息</strong></td>
			</tr>
			<tr>
				<td>28</td>
				<td>现住址</td>
				<td><input id="ipt_other15" name="other15" value="$J{other15}" style="width: 95%;" type="text" class="inp" maxlength="26" placeholder="按户口簿填写，但请将字数精简到26个字以内。" /></td>
				<td>32</td>
				<td>邮政编码</td>
				<td><input id="ipt_other19" name="other19" value="$J{other19}" style="width: 95%;" type="text" class="inp" /></td>
			</tr>
			<tr>
				<td>29</td>
				<td>通信地址</td>
				<td><input id="ipt_other16" name="other16" value="$J{other16}" style="width: 95%;" type="text" class="inp" maxlength="26" placeholder="26字以内" /></td>
				<td>33</td>
				<td>电子信箱</td>
				<td><input id="ipt_other20" name="other20" value="$J{other20}" style="width: 95%;" type="text" class="inp" /></td>
			</tr>
			<tr>
				<td>30</td>
				<td>家庭地址</td>
				<td><input id="ipt_other17" name="other17" value="$J{other17}" style="width: 95%;" type="text" class="inp" maxlength="26" placeholder="请填写武汉市内居住的小区地址。可填写租住地址。" /></td>
				<td>34</td>
				<td>主页地址</td>
				<td><input id="ipt_other21" name="other21" value="$J{other21}" style="width: 95%;" type="text" class="inp" /></td>
			</tr>
			<tr>
				<td>31</td>
				<td>联系电话</td>
				<td><input id="ipt_other18" name="other18" value="$J{other18}" style="width: 95%;" type="text" class="inp" /></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td colspan="6"><strong>学生个人扩展信息</strong></td>
			</tr>
			<tr>
				<td>35</td>
				<td>是否独生子女</td>
				<td>
					<label><input name="other22" {@if other22=="是"}checked="checked"{@/if} type="radio" value="是" />是</label>
					<label><input name="other22" {@if other22=="否"}checked="checked"{@/if} type="radio" value="否" />否</label>
				</td>
				<td>41</td>
				<td>随班就读</td>
				<td>
					<select id="sel_other28" name="other28" class="seleCss" style="width: 100px;">
						<option value="">请选择</option>
						<option value="非随班就读">非随班就读</option>
						<option value="视力残疾随班就读">视力残疾随班就读</option>
						<option value="听力残疾随班就读">听力残疾随班就读</option>
						<option value="智力残疾随班就读">智力残疾随班就读</option>
						<option value="其他残疾随班就读">其他残疾随班就读</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>36</td>
				<td>是否受过学前教育</td>
				<td>
					<label><input name="other23" {@if other23=="是"}checked="checked"{@/if} type="radio" value="是" />是</label>
					<label><input name="other23" {@if other23=="否"}checked="checked"{@/if} type="radio" value="否" />否</label>
				</td>
				<td>42</td>
				<td>残疾类型</td>
				<td>
					<select id="sel_other29" name="other29" class="seleCss" style="width: 100px;">
						<option value="">请选择</option>
						<option value="无残疾">无残疾</option>
						<option value="视力残疾">视力残疾</option>
						<option value="听力残疾">听力残疾</option>
						<option value="智力残疾">智力残疾</option>
						<option value="言语残疾">言语残疾</option>
						<option value="肢体残疾">肢体残疾</option>
						<option value="精神残疾">精神残疾</option>
						<option value="多重残疾">多重残疾</option>
						<option value="其他残疾">其他残疾</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>37</td>
				<td>是否留守儿童</td>
				<td>$J{other24}</td>
				<td>43</td>
				<td>是否由政府购买学位</td>
				<td>$J{other30}</td>
			</tr>
			<tr>
				<td>38</td>
				<td>是否进城务工人员随迁子女</td>
				<td>
					{@if stuApply.other4=="农业户口"}
					是<input name="other25" type="hidden" value="是" />
					{@else}
					否<input name="other25" type="hidden" value="否" />
					{@/if}
				</td>
				<td>44</td>
				<td>是否需要申请资助</td>
				<td>$J{other31}</td>
			</tr>
			<tr>
				<td>39</td>
				<td>是否孤儿</td>
				<td>
					<label><input name="other26" {@if other26=="是"}checked="checked"{@/if} type="radio" value="是" />是</label>
					<label><input name="other26" {@if other26=="否"}checked="checked"{@/if} type="radio" value="否" />否</label>
				</td>
				<td>45</td>
				<td>是否享受一补</td>
				<td>
					<label><input name="other32" {@if other32=="是"}checked="checked"{@/if} type="radio" value="是" />是</label>
					<label><input name="other32" {@if other32=="否"}checked="checked"{@/if} type="radio" value="否" />否</label>
				</td>
			</tr>
			<tr>
				<td>40</td>
				<td>是否烈士或优抚子女</td>
				<td>
					<label><input name="other27" {@if other27=="是"}checked="checked"{@/if} type="radio" value="是" />是</label>
					<label><input name="other27" {@if other27=="否"}checked="checked"{@/if} type="radio" value="否" />否</label>
				</td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td colspan="6"><strong>学生上下学交通方式</strong></td>
			</tr>
			<tr>
				<td>46</td>
				<td>上下学距离（公里）</td>
				<td><input id="ipt_other33" name="other33" value="$J{other33}" style="width: 95%;" type="text" class="inp" /></td>
				<td>48</td>
				<td>是否需要乘坐校车</td>
				<td>
					<label><input name="other35" {@if other35=="是"}checked="checked"{@/if} type="radio" value="是" />是</label>
					<label><input name="other35" {@if other35=="否"}checked="checked"{@/if} type="radio" value="否" />否</label>
				</td>
			</tr>
			<tr>
				<td>47</td>
				<td>上下学交通方式</td>
				<td>
					<select id="sel_other34" name="other34" class="seleCss" style="width: 100px;">
						<option value="">请选择</option>
						<option value="步行">步行</option>
						<option value="自行车（含摩托车、电动自行车）">自行车（含摩托车、电动自行车）</option>
						<option value="公共交通（含城市公交、农村客运和地铁）">公共交通（含城市公交、农村客运和地铁）</option>
						<option value="家长自行接送">家长自行接送</option>
						<option value="校车">校车</option>
						<option value="其他">其他</option>
					</select>
				</td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td colspan="6"><strong>学生家庭成员或监护人信息一</strong></td>
			</tr>
			<tr>
				<td>49</td>
				<td>姓名</td>
				<td><input id="ipt_other36" name="other36" value="$J{other36}" style="width: 95%;" type="text" class="inp" /></td>
				<td>55</td>
				<td>户口所在地</td>
				<td><input id="ipt_other42" name="other42" value="$J{other42}" style="width: 95%;" type="text" class="inp"  placeholder="请下载“行政区划代码查询表”并参照该表输入相应值!"/></td>
			</tr>
			<tr>
				<td>50</td>
				<td>关系</td>
				<td>$J{other37}</td>
				<td>56</td>
				<td>联系电话</td>
				<td><input id="ipt_other43" name="other43" value="$J{other43}" style="width: 95%;" type="text" class="inp" /></td>
			</tr>
			<tr>
				<td>51</td>
				<td>关系说明</td>
				<td>$J{other38}</td>
				<td>57</td>
				<td>是否监护人</td>
				<td>
					<label><input name="other44" {@if other44=="是"}checked="checked"{@/if} type="radio" value="是" />是</label>
					<label><input name="other44" {@if other44=="否"}checked="checked"{@/if} type="radio" value="否" />否</label>
				</td>
			</tr>
			<tr>
				<td>52</td>
				<td>民族</td>
				<td><jsp:include flush="true" page="/WEB-INF/content/student/sel/sel_nation_other39.jsp" /></td>
				<td>58</td>
				<td>身份证件类型</td>
				<td>
					<select id="sel_other45" name="other45" class="seleCss" style="width: 100px;">
						<option value="CARD_TYPE1">居民身份证</option>
						<option value="CARD_TYPE2">香港特区护照、身份证明</option>
						<option value="CARD_TYPE3">澳门特区护照/身份证明</option>
						<option value="CARD_TYPE4">台湾居民来往大陆通行证</option>
						<option value="CARD_TYPE5">境外永久居住证</option>
						<option value="CARD_TYPE6">护照</option>
						<option value="CARD_TYPE7">其他</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>53</td>
				<td>工作单位</td>
				<td><input id="ipt_other40" name="other40" value="$J{other40}" style="width: 95%;" type="text" maxlength="10" class="inp" placeholder="10字以内，如：华师文学院，中国银行武汉分行等。" /></td>
				<td>59</td>
				<td>身份证件号</td>
				<td><input id="ipt_other46" name="other46" value="$J{other46}" style="width: 95%;" type="text" class="inp" /></td>
			</tr>
			<tr>
				<td>54</td>
				<td>现住址</td>
				<td><input id="ipt_other41" name="other41" value="$J{other41}" style="width: 95%;" type="text" class="inp" maxlength="26" placeholder="按户口簿填写，但请将字数精简到26个字以内。" /></td>
				<td>60</td>
				<td>职务</td>
				<td><input id="ipt_other47" name="other47" value="$J{other47}" style="width: 95%;" type="text" class="inp" /></td>
			</tr>
			<tr>
				<td colspan="6"><strong>学生家庭成员或监护人信息二</strong></td>
			</tr>
			<tr>
				<td>61</td>
				<td>姓名</td>
				<td><input id="ipt_other48" name="other48" value="$J{other48}" style="width: 95%;" type="text" class="inp" /></td>
				<td>67</td>
				<td>户口所在地</td>
				<td><input id="ipt_other54" name="other54" value="$J{other54}" style="width: 95%;" type="text" class="inp"  placeholder="请下载“行政区划代码查询表”并参照该表输入相应值!"/></td>
			</tr>
			<tr>
				<td>62</td>
				<td>关系</td>
				<td>$J{other49}</td>
				<td>68</td>
				<td>联系电话</td>
				<td><input id="ipt_other55" name="other55" value="$J{other55}" style="width: 95%;" type="text" class="inp" /></td>
			</tr>
			<tr>
				<td>63</td>
				<td>关系说明</td>
				<td>$J{other50}</td>
				<td>69</td>
				<td>是否监护人</td>
				<td>
					<label><input name="other56" {@if other56=="是"}checked="checked"{@/if} type="radio" value="是" />是</label>
					<label><input name="other56" {@if other56=="否"}checked="checked"{@/if} type="radio" value="否" />否</label>
				</td>
			</tr>
			<tr>
				<td>64</td>
				<td>民族</td>
				<td><jsp:include flush="true" page="/WEB-INF/content/student/sel/sel_nation_other51.jsp" /></td>
				<td>70</td>
				<td>身份证件类型</td>
				<td>
					<select id="sel_other57" name="other57" class="seleCss" style="width: 100px;">
						<option value="CARD_TYPE1">居民身份证</option>
						<option value="CARD_TYPE2">香港特区护照、身份证明</option>
						<option value="CARD_TYPE3">澳门特区护照/身份证明</option>
						<option value="CARD_TYPE4">台湾居民来往大陆通行证</option>
						<option value="CARD_TYPE5">境外永久居住证</option>
						<option value="CARD_TYPE6">护照</option>
						<option value="CARD_TYPE7">其他</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>65</td>
				<td>工作单位</td>
				<td><input id="ipt_other52" name="other52" value="$J{other52}" style="width: 95%;" maxlength="10" type="text" class="inp" placeholder="10字以内，如：华师文学院，中国银行武汉分行等。" /></td>
				<td>71</td>
				<td>身份证件号</td>
				<td><input id="ipt_other58" name="other58" value="$J{other58}" style="width: 95%;" type="text" class="inp" /></td>
			</tr>
			<tr>
				<td>66</td>
				<td>现住址</td>
				<td><input id="ipt_other53" name="other53" value="$J{other53}" style="width: 95%;" type="text" class="inp" maxlength="26" placeholder="按户口簿填写，但请将字数精简到26个字以内。" /></td>
				<td>72</td>
				<td>职务</td>
				<td><input id="ipt_other59" name="other59" value="$J{other59}" style="width: 95%;" type="text" class="inp" /></td>
			</tr>
		</tbody>
	</table>
	</form>