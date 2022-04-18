<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="数据编码" prop="catalogCode">
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
      <el-form-item label="数据描述" prop="productDescription">
        <el-input
          v-model="queryParams.productDescription"
          placeholder="请输入数据描述"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="数据类型" prop="dataFormat">
        <el-input
          v-model="queryParams.dataFormat"
          placeholder="请输入数据类型"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建时间" prop="createDate">
        <el-date-picker clearable
          v-model="queryParams.createDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择时间">
        </el-date-picker>
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
          v-hasPermi="['system:image:add']"
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
          v-hasPermi="['system:image:edit']"
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
          v-hasPermi="['system:image:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:image:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="imageList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="数据ID" align="center" prop="imageId" />
      <el-table-column label="数据编码" align="center" prop="catalogCode" />
      <el-table-column label="数据名称" align="center" prop="productName" />
      <el-table-column label="数据描述" align="center" prop="productDescription" />
      <el-table-column label="数据类型" align="center" prop="dataFormat" />
      <el-table-column label="数据分布" align="center" prop="filePath" />
      <el-table-column label="数据大小" align="center" prop="dataSize" />
      <el-table-column label="数据时间" align="center" prop="createDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:image:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:image:remove']"
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

    <!-- 添加或修改数据资源管理对话框 -->
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
import { listImage, getImage, delImage, addImage, updateImage } from "@/api/system/image";

export default {
  name: "Image",
  data() {
    return {
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
      // 数据资源管理表格数据
      imageList: [],
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
  },
  methods: {
    /** 查询数据资源管理列表 */
    getList() {
      this.loading = true;
      listImage(this.queryParams).then(response => {
        this.imageList = response.rows;
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
        downloadTime: null,
        modifyDate: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
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
      this.title = "添加数据资源管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const imageId = row.imageId || this.ids
      getImage(imageId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改数据资源管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.imageId != null) {
            updateImage(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addImage(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除数据资源管理编号为"' + imageIds + '"的数据项？').then(function() {
        return delImage(imageIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/image/export', {
        ...this.queryParams
      }, `image_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
