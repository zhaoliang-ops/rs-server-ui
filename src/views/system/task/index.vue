<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单编码" prop="catalogCode">
        <el-input
          v-model="queryParams.catalogCode"
          placeholder="请输入数据编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="数据名称" prop="productName">
        <el-input
          v-model="queryParams.productName"
          placeholder="请输入数据名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="任务状态" prop="productDescription">
        <el-input
          v-model="queryParams.productDescription"
          placeholder="请输入数据描述"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
       <el-form-item label="操作主机" prop="productDescription">
        <el-input
          v-model="queryParams.productDescription"
          placeholder="请输入数据描述"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:localimage:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:localimage:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:localimage:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:localimage:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="localimageList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="订单号" align="center" prop="imageId" />
      <el-table-column label="数据名称" align="center" prop="productName" />
      <el-table-column label="当前状态" align="center" prop="promotionId" >
          <template slot-scope="scope">
            <span v-if="scope.row.promotionId === 1">下载中</span>  
            <span v-else-if="scope.row.promotionId === 3">上传中</span> 
            <span v-else-if="scope.row.promotionId === 2">已完成</span>
          </template>
      </el-table-column>
      <el-table-column label="数据大小(MB)" align="center" prop="dataSize" />
      <el-table-column
                        prop="diskId"
                        align="center"
                        label="任务进度">
          <template slot-scope="scope">
            <el-progress :text-inside="true" :stroke-width="18" :percentage="scope.row.diskId" status="success"></el-progress>
          </template>
      </el-table-column>
      <!-- <el-table-column label="调度算法" align="center" prop="algorithm" width="180"/> -->
      <!-- <el-table-column label="下载时间" align="center" prop="downloadTime" width="180"/> -->
      <!-- <el-table-column label="状态" align="center" prop="wrsRow" >
          <template slot-scope="scope">
              <el-switch
                    v-model="scope.row.wrsRow"
                    :active-value="0"
                    :inactive-value="1  "
                    active-color="#02538C"
                    inactive-color="#B9B9B9"
                    @change="changeSwitch(scope.row)"/>
          </template>
      </el-table-column> -->
      <el-table-column label="操作主机" align="center" prop="creator" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:localimage:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改本地资源管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="${comment}" prop="catalogCode">
          <el-input v-model="form.catalogCode" placeholder="请输入${comment}" />
        </el-form-item>
        <el-form-item label="${comment}" prop="productName">
          <el-input v-model="form.productName" placeholder="请输入${comment}" />
        </el-form-item>
        <el-form-item label="${comment}" prop="productDescription">
          <el-input v-model="form.productDescription" placeholder="请输入${comment}" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listLocalimage, getLocalimage,getDownLoadLocalimageList, delLocalimage, addLocalimage, updateLocalimage,downloadP2PLocalimage } from "@/api/system/localimage";
import { getAllTaskList} from "@/api/system/image";
import { listAlgorithm, getAlgorithm, delAlgorithm, addAlgorithm, updateAlgorithm } from "@/api/system/algorithm";
export default {
  name: "Localimage",
  data() {
    return {
      intAlgorithm: 0,
      sysAlgorithm: '',
      random: 0,
      percentage: 0, // 进度条的占比
      timer: '',
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 本地资源管理表格数据
      localimageList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        catalogCode: null,
        productName: null,
        productDescription: null,
        dataFormat: null,
        createDate: null,
        promotionId:null,
        imageId:null,
        diskId:0,
        downloadTime:null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        catalogCode: [
          { required: true, message: "$comment不能为空", trigger: "blur" }
        ],
        productName: [
          { required: true, message: "$comment不能为空", trigger: "blur" }
        ],
        productDescription: [
          { required: true, message: "$comment不能为空", trigger: "blur" }
        ],
        spatialResolution: [
          { required: true, message: "$comment不能为空", trigger: "blur" }
        ],
        cloudPercent: [
          { required: true, message: "$comment不能为空", trigger: "blur" }
        ],
        sunElevation: [
          { required: true, message: "$comment不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    // alert(11)
    // this.getAlgList()
      // 定时器
    // this.start()
  },
  methods: {
      changeSwitch(row) {
      // console.log(row)
        updateLocalimage(row).then(response => {
          this.$modal.msgSuccess("更新状态成功");
          this.open = false;
          this.getList();
          this.start();
        });
      },
        // 随机数
        get_random_v(size) {
          var min = size - 10
          var max = size + 10
          var result = Math.round(Math.random() * (max - min) + min)
          return result
        },
        // 计算时间
      getDownloadTime(size) {
        // 随机数
        var random1 = this.get_random_v(size)
        // 随机数赋全局
        this.random = random1
        // 估计所用时间
        var filit = random1 / 100
        var result = parseFloat(this.byAlgGetTime(100 / filit * 2)).toFixed(2)
        return result
      },
      getAddRandom(size) {
      var alg = this.intAlgorithm
      var result = size
      if (alg === 1) {
        result = size + 10
      } else if (alg === 2) {
        result = size + 7
      } else if (alg === 3) {
        result = size + 3
      } else if (alg === 4) {
        result = size + 2
      }
      var min = result - 2
      var max = result + 2
      var res = Math.round(Math.random() * (max - min) + min)
      return res
    },
    byAlgGetTime(time) {
      var alg = this.intAlgorithm
      var result = time
      if (alg === 1) {
        result = time - 10
      } else if (alg === 2) {
        result = time - 7
      } else if (alg === 3) {
        result = time - 3
      } else if (alg === 4) {
        result = time - 2
      }
      return result
    },
    getAlgorithmNameZh(context) {
      var result = '无'
      if (context === 1) {
        result = 'MGA-PSO算法'
      } else if (context === 2) {
        result = 'GA-PSO算法'
      } else if (context === 3) {
        result = 'GA算法'
      } else if (context === 4) {
        result = 'PSO算法'
      }
      return result
    },
    // 每个两秒增加下载进度
    start() {
      this.timer = setInterval(this.getProgress, 2000) // 注意: 第一个参数为方法名的时候不要加括号;
    },
    getAlgNumByName(){
      var result = 0
      if (this.sysAlgorithm =='MGA-PSO算法') {
        result = 1
      } else if (this.sysAlgorithm == 'GA-PSO算法') {
        result = 2
      } else if (this.sysAlgorithm =='GA算法') {
        result = 3
      } else if (this.sysAlgorithm === 'PSO算法') {
        result = 4
      }
      return result
    },
        /** 查询调度算法管理列表 */
    getAlgList() {
      // this.loading = true;
     var params = {
        pageNum: 1,
        pageSize: 1,
        isActive: 1
      }
      listAlgorithm(params).then(response => {
        if(response.rows.length != 0){
          this.intAlgorithm = response.rows[0].id
          this.sysAlgorithm = response.rows[0].nameZh
        }
      });
    },
    getProgress() {
      this.queryParams.pageNum = 1;
      this.getList();
     var downList = this.localimageList
     var downNum = 0;
     for(var i=0;i<downList.length;i++){
       var status = downList[i].promotionId
       if(status ==1 ||status==3 ){
          if(downList[i].diskId == 100){
            this.queryParams.imageId = downList[i].imageId
            this.queryParams.promotionId = 2
            this.queryParams.downloadTime = status = 1 ? this.getDownloadTime(downList[i].dataSize):''
            downloadP2PLocalimage(this.queryParams).then(response => {
          })
         }else{
            // 下载
            downNum++
           if(status = 1){
            this.random = this.get_random_v(downList[i].dataSize)
            // 随机更新每次新增多少
            var filit = this.getAddRandom(this.random / 100)
            var percentage = downList[i].diskId + filit > 100 ? 100 : downList[i].diskId + filit
            this.queryParams.imageId = downList[i].imageId
            this.queryParams.promotionId = 1
            this.queryParams.diskId = percentage
           }else{
            // 上传
            this.random = this.get_random_v(1000)
            // 随机更新每次新增多少
            var filit = this.random / 100
            var percentage = downList[i].wrsPath + filit > 100 ? 100 : downList[i].wrsPath + filit
            this.queryParams.imageId = downList[i].imageId
            this.queryParams.promotionId = 3
            this.queryParams.wrsPath = parseFloat(percentage).toFixed(0) 
           }
            downloadP2PLocalimage(this.queryParams).then(response => {
          })
         }
       }
     }
    //  console.log(downNum)
     // 无下载中任务，清除定时器
     if(downNum == 0){
          clearInterval(this.timer)
     }
    },
    /** 查询本地资源管理列表 */
    getList() {
      this.loading = true;
      getAllTaskList(this.queryParams).then(response => {
        this.localimageList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        imageId: null,
        catalogCode: null,
        promotionId: null,
        productName: null,
        productPrice: null,
        productDescription: null,
        satelliteId: null,
        sensorId: null,
        dataFormat: null,
        mapProjection: null,
        earthModel: null,
        spatialResolution: null,
        version: null,
        standardCode: null,
        imageCode: null,
        md5: null,
        diskId: null,
        serverId: null,
        wrsPath: null,
        wrsRow: null,
        thumbView: null,
        quickView: null,
        cloudPercent: null,
        filePath: null,
        dataSize: null,
        centerLat: null,
        centerLong: null,
        upperLeftLat: null,
        upperLeftLong: null,
        lowerRightLat: null,
        lowerRightLong: null,
        geom: null,
        dataUnitId: null,
        sourceProductId: null,
        area: null,
        sunElevation: null,
        sensingStartDate: null,
        sensingEndDate: null,
        datasetName: null,
        productType: null,
        productDate: null,
        productLevel: null,
        bands: null,
        upperRightLat: null,
        upperRightLong: null,
        lowerLeftLat: null,
        lowerLeftLong: null,
        recStationId: null,
        algorithm: null,
        temporalResolution: null,
        creator: null,
        createDate: null,
        modifier: null,
        modifyDate: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      // alert(111)
      this.queryParams.pageNum = 1;
      this.getList();
      // this.getProgress()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.imageId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加本地资源管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const imageId = row.imageId || this.ids
      getLocalimage(imageId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改本地资源管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.imageId != null) {
            updateLocalimage(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addLocalimage(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const imageIds = row.imageId || this.ids;
      this.$modal.confirm('是否确认取消该编号为"' + imageIds + '"的遥感数据所处任务？').then(function() {
        // return delLocalimage(imageIds);
        row.promotionId = 0
        row.diskId = 0
        row.downloadTime=''
        updateLocalimage(row)
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("取消成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/localimage/export', {
        ...this.queryParams
      }, `localimage_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
