<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公网IP" prop="ip">
        <el-input
          v-model="queryParams.ip"
          placeholder="请输入公网IP"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户名称" prop="username">
        <el-input
          v-model="queryParams.username"
          placeholder="请输入用户名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="系统环境" prop="env">
        <el-input
          v-model="queryParams.env"
          placeholder="请输入系统环境"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="机器名称" prop="macName">
        <el-input
          v-model="queryParams.macName"
          placeholder="请输入机器名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="在线时间" prop="lastActiveTime">
        <el-date-picker clearable
          v-model="queryParams.lastActiveTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择在线时间">
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
          v-hasPermi="['system:peer:add']"
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
          v-hasPermi="['system:peer:edit']"
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
          v-hasPermi="['system:peer:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:peer:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="peerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="公网IP" align="center" prop="ip" />
      <el-table-column label="用户名称" align="center" prop="username" />
      <el-table-column label="系统环境" align="center" prop="env" />
      <el-table-column label="机器名称" align="center" prop="macName" />
      <el-table-column label="最大任务数" align="center" prop="maxTask" />
      <el-table-column label="是否活跃" align="center" prop="isActive" />
      <el-table-column label="在线时间" align="center" prop="lastActiveTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.lastActiveTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-popover ref="popover4" placement="left-start" width="620" trigger="click" >
                 <div style="margin-left:10px;font-size:15px">
              <i class="el-icon-date" style="color:#20A0FF"></i>  <span style="margin-left:8px;color:#20A0FF">节点详情</span>
                </div>
                  <div class="login-bodya">
                   <div class="loginWarpa">
                     <div class="login-forma">
                    <el-form ref="form"  label-width="0">
                    <el-form-item prop="old_password" class="login-itema" style="margin-top:8px;margin-left:8px">
                    <label >公网IP ：</label><span style="color:teal">{{project_data.ip}}</span>
                    </el-form-item>
                      <el-form-item prop="old_password" class="login-itema" style="margin-top:4px;margin-left:8px">
                    <label >节点环境 ：</label> <span style="color:teal">{{project_data.env == 1 ? 'Windows环境' : 'Linux环境'}}</span>
                    </el-form-item>
                     <el-form-item prop="old_password" class="login-itema" style="margin-top:4px;margin-left:8px">
                      <label >资源地址 ：</label> <span style="color:teal"> {{project_data.ip}}/rsdata </span>
                      </el-form-item>
                    <el-form-item prop="old_password" class="login-itema" style="margin-top:4px;margin-left:8px">
                   <label >磁盘 ：</label><el-progress :text-inside="true" :stroke-width="18" :percentage="70"></el-progress>
                    </el-form-item>
                      <el-form-item prop="old_password" class="login-itema" style="margin-top:4px;margin-left:8px">
                    <label >内存 ：</label> <el-progress :text-inside="true" :stroke-width="18" :percentage="80" status="success"></el-progress>
                    </el-form-item>
                     <el-form-item prop="old_password" class="login-itema" style="margin-top:4px;margin-left:8px">
                      <label >CPU ：</label> <el-progress :text-inside="true" :stroke-width="18" :percentage="50" status="exception"></el-progress>
                      </el-form-item>
                      <el-form-item prop="old_password" class="login-itema" style="margin-top:8px;margin-left:8px">
                     <label >用户 ：</label><span style="color:teal">{{project_data.username}}</span>
                     <label style="margin-left:22px">最大任务数 ：</label><span style="color:teal">{{project_data.maxTask}}</span>
                      </el-form-item>
                       </el-form>
                    </div>
                  </div>
                </div>
                <div style="margin-left:10px;font-size:15px">
                <i class="el-icon-menu" style="color:#13CE66"></i> <span style="margin-top:8px;margin-left:8px;color:#13CE66">任务详情</span>
                </div>
                  <div class="login-bodya">
                   <div class="loginWarpa">
                     <div class="login-forma">
                    <el-form ref="form"  label-width="0">
                    <el-form-item prop="old_password" class="login-itema" style="margin-top:8px;margin-left:8px">
                         <label>上传任务:</label><el-tag class="el-icon-question" style="margin-left:2px">待定</el-tag><span  style=" width:15px;color:teal">{{upload_d}}</span>
                            <el-tag class="el-icon-success" type="success" style="margin-left:2px">已完成</el-tag><span style="width:15px;color:teal;margin-left:2px">{{upload_s}}</span>
                            <el-tag class="el-icon-error" type="info" style="margin-left:2px">已取消</el-tag><span style="width:15px;color:teal;margin-left:2px">{{upload_q}}</span>
                            <el-tag class="el-icon-info" type="warning" style="margin-left:2px">进行中</el-tag><span style="width:15px;color:teal;margin-left:2px">{{upload_j}}</span>
                            <el-tag class="el-icon-warning" type="danger" style="margin-left:2px">异常</el-tag><span style="width:15px;color:teal;margin-left:2px">{{upload_y}}</span>
                    </el-form-item>
                    <el-form-item prop="old_password" class="login-itema" style="margin-top:8px;margin-left:8px">
                           <label>下载任务:</label><el-tag class="el-icon-question" style="margin-left:2px">待定</el-tag><span style="width:15px;color:teal">{{download_d}}</span>
                            <el-tag class="el-icon-success" type="success" style="margin-left:2px">已完成</el-tag><span style="width:15px;color:teal;margin-left:2px">{{download_s}}</span>
                            <el-tag class="el-icon-error" type="info" style="margin-left:2px">已取消</el-tag><span style="width:15px;color:teal;margin-left:2px">{{download_q}}</span>
                            <el-tag class="el-icon-info" type="warning" style="margin-left:2px">进行中</el-tag><span style="width:15px;color:teal;margin-left:2px">{{download_j}}</span>
                            <el-tag class="el-icon-warning" type="danger" style="margin-left:2px">异常</el-tag><span style="width:15px;color:teal;margin-left:2px">{{download_y}}</span>
                    </el-form-item>
                       </el-form>
                    </div>
                  </div>
                </div>
                </el-popover>
        <el-button type="success" v-popover:popover4 size="small"  icon="search" @click="get_random_v(scope.row)">查看
        </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:peer:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:peer:remove']"
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

    <!-- 添加或修改对等成员对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="公网IP" prop="ip">
          <el-input v-model="form.ip" placeholder="请输入公网IP" />
        </el-form-item>
        <el-form-item label="用户名称" prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名称" />
        </el-form-item>
        <el-form-item label="系统环境" prop="env">
          <el-input v-model="form.env" placeholder="请输入系统环境" />
        </el-form-item>
        <el-form-item label="机器名称" prop="macName">
          <el-input v-model="form.macName" placeholder="请输入机器名称" />
        </el-form-item>
        <el-form-item label="最大任务数" prop="maxTask">
          <el-input v-model="form.maxTask" placeholder="请输入最大任务数" />
        </el-form-item>
        <el-form-item label="最后在线时间" prop="lastActiveTime">
          <el-date-picker clearable
            v-model="form.lastActiveTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择最后在线时间">
          </el-date-picker>
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
import { listPeer, getPeer, delPeer, addPeer, updatePeer } from "@/api/system/peer";

export default {
  name: "Peer",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      upload_d: '0',
      upload_s: '0',
      upload_q: '0',
      upload_j: '0',
      upload_y: '0',
      download_d: '0',
      download_s: '0',
      download_q: '0',
      download_j: '0',
      download_y: '0', 
      project_data:[],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 对等成员表格数据
      peerList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        ip: null,
        username: null,
        env: null,
        macName: null,
        isActive: null,
        lastActiveTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        ip: [
          { required: true, message: "公网IP不能为空", trigger: "blur" }
        ],
        username: [
          { required: true, message: "用户名称不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
        get_random_v(data){
        this.project_data = data
        var v_1 = Math.round(Math.random()*115)
        var v_2 = Math.round(Math.random()*215)
        var v_3 = Math.round(Math.random()*315)
        var v_4 = Math.round(Math.random()*425)
        var v_5 = Math.round(Math.random()*315)
        var v_6 = Math.round(Math.random()*225)
        var v_7 = Math.round(Math.random()*365)
        var v_8 = Math.round(Math.random()*525)
        var v_9 = Math.round(Math.random()*415)
        var v_10 = Math.round(Math.random()*225)
        this.upload_d= v_1
        this.upload_s= v_2
        this.upload_q = v_3
        this.upload_j = v_4
        this.upload_y = v_5
        this.download_d =v_6
        this.download_s =v_7
        this.download_q =v_8
        this.download_j =v_9
        this.download_y =v_10
    },
    /** 查询对等成员列表 */
    getList() {
      this.loading = true;
      listPeer(this.queryParams).then(response => {
        this.peerList = response.rows;
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
        id: null,
        ip: null,
        username: null,
        env: null,
        macName: null,
        maxTask: null,
        isActive: null,
        lastActiveTime: null,
        createTime: null
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加对等成员";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getPeer(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改对等成员";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePeer(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPeer(this.form).then(response => {
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
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除对等成员编号为"' + ids + '"的数据项？').then(function() {
        return delPeer(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/peer/export', {
        ...this.queryParams
      }, `peer_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
