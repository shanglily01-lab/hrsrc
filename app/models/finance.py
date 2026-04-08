from sqlalchemy import Column, Integer, String, Text, Numeric
from app.database import Base


class FinEmployee(Base):
    __tablename__ = "fin_employee"

    id = Column(Integer, primary_key=True)
    tg_name = Column(Text)
    position = Column(Text)
    entry_date = Column(Text)
    trial_salary = Column(Text)
    formal_salary = Column(Text)
    adjusted_salary = Column(Text)
    salary_adjust_date = Column(Text)
    is_formal = Column(Text)
    team = Column(Text)
    currency = Column(Text)    # U / RMB - settlement currency
    saddr = Column(Text)       # TRC20 wallet address
    vaddr = Column(Text)       # ERC20 wallet address
    remarks = Column(Text)
    created_at = Column(Text)
    updated_at = Column(Text)


class FinSalaryRecord(Base):
    __tablename__ = "fin_salary_record"

    id = Column(Integer, primary_key=True)
    year_label = Column(Text)           # 年份标签 e.g. "2024"
    month = Column(Text)                # YYYY-MM, auto-parsed from period
    tg_name = Column(Text)
    position = Column(Text)             # 岗位
    base_salary = Column(Numeric(30, 4))
    performance_salary = Column(Numeric(30, 4))
    bonus = Column(Numeric(30, 4))      # 奖金RMB
    expense_rmb = Column(Numeric(30, 4))    # 报销金额RMB
    expense_u = Column(Numeric(30, 4))      # 报销金额U
    reward_amount = Column(Numeric(30, 4))  # 奖励金额（同结算货币）
    penalty_amount = Column(Numeric(30, 4)) # 处罚金额（同结算货币）
    paid_rmb = Column(Numeric(30, 4))   # 应发工资RMB（不含报销）
    period = Column(Text)               # 时间段 e.g. "09.10～09.25"
    remarks = Column(Text)              # 备注（小计列）
    wallet_address = Column(Text)       # 收款地址TRC20
    exchange_rate = Column(Numeric(30, 4))
    paid_u = Column(Numeric(30, 4))     # U数量
    actual_amount = Column(Numeric(30, 4))  # 实际发放
    payment_date = Column(Text)         # 支付日期
    status = Column(Text)               # PAID / UNPAID
    source = Column(Text)               # excel / monthly_pay / manual
    created_at = Column(Text)
    updated_at = Column(Text)


class FinExpense(Base):
    __tablename__ = "fin_expense"

    id = Column(Integer, primary_key=True)
    category = Column(Text)
    description = Column(Text)
    amount = Column(Numeric(30, 4))
    currency = Column(Text)
    expense_date = Column(Text)
    applicant = Column(Text)
    status = Column(Text)
    image_path = Column(Text)       # uploaded receipt image path
    remarks = Column(Text)
    expense_month = Column(Text)    # YYYY-MM 报销月份
    created_at = Column(Text)
    updated_at = Column(Text)


class FinMonthlyPayment(Base):
    __tablename__ = "fin_monthly_payment"

    id = Column(Integer, primary_key=True)
    month = Column(Text)               # YYYY-MM
    team = Column(Text)                # web3团队 / AI团队 / 分布式团队
    tg_name = Column(Text)             # employee TG name
    currency = Column(Text)            # U / RMB
    amount = Column(Numeric(30, 4))    # amount in specified currency
    exchange_rate = Column(Numeric(10, 4))  # U→RMB rate
    wallet_address = Column(Text)      # TRC20 wallet address
    remarks = Column(Text)
    status = Column(Text)              # UNPAID / PAID
    created_at = Column(Text)
    updated_at = Column(Text)


class FinFundRequest(Base):
    __tablename__ = "fin_fund_request"

    id = Column(Integer, primary_key=True)
    apply_month = Column(Text)          # YYYY-MM 申请月份
    category = Column(Text)             # 办公室费用 / 合约部署 / 测试资金
    currency = Column(Text)             # U / RMB
    amount = Column(Numeric(30, 4))     # 申请金额
    exchange_rate = Column(Numeric(10, 4))  # U→CNY 汇率
    purpose = Column(Text)              # 用途说明
    applicant = Column(Text)            # 申请人
    status = Column(Text)               # PENDING / APPROVED / REJECTED
    approved_by = Column(Text)          # 审批人
    received_at = Column(Text)          # 申请人确认到账时间，NULL=未到账
    remarks = Column(Text)
    created_at = Column(Text)
    updated_at = Column(Text)


class FinFundUsage(Base):
    __tablename__ = "fin_fund_usage"

    id = Column(Integer, primary_key=True)
    usage_month = Column(Text)          # YYYY-MM
    currency = Column(Text)             # U / RMB
    amount = Column(Numeric(30, 4))
    category = Column(Text)             # 薪资 / 运营 / 推广 / 其他
    description = Column(Text)          # 使用说明
    operator = Column(Text)             # 操作人
    remarks = Column(Text)
    created_at = Column(Text)
    updated_at = Column(Text)


class FinRewardPenalty(Base):
    __tablename__ = "fin_reward_penalty"

    id = Column(Integer, primary_key=True)
    month = Column(Text)                    # YYYY-MM 生效月份
    tg_name = Column(Text)                  # 员工TG名
    type = Column(Text)                     # REWARD / PENALTY
    amount = Column(Numeric(30, 4))         # 金额（始终为正）
    currency = Column(Text)                 # U / RMB
    reason = Column(Text)                   # 原因说明
    created_by = Column(Text)
    created_at = Column(Text)
    updated_at = Column(Text)


class FinFundRecord(Base):
    __tablename__ = "fin_fund_record"

    id = Column(Integer, primary_key=True)
    period = Column(Text)
    network = Column(Text)
    asset_type = Column(Text)
    recharge_amount = Column(Numeric(30, 4))
    withdraw_amount = Column(Numeric(30, 4))
    contract_balance = Column(Numeric(30, 4))
    owner_balance = Column(Numeric(30, 4))
    record_date = Column(Text)
    remarks = Column(Text)
    created_at = Column(Text)
    updated_at = Column(Text)
