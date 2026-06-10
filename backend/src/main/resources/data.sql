INSERT INTO record_entity (name, description)
SELECT * FROM (SELECT '新生报到', '包含报到须知、材料清单与流程说明') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM record_entity WHERE name = '新生报到') LIMIT 1;

INSERT INTO record_entity (name, description)
SELECT * FROM (SELECT '课程安排', '展示学期课程表与时间地点') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM record_entity WHERE name = '课程安排') LIMIT 1;

INSERT INTO record_entity (name, description)
SELECT * FROM (SELECT '活动通知', '校园活动与社团招新信息') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM record_entity WHERE name = '活动通知') LIMIT 1;

INSERT INTO equipment (name, model, location, description, status)
SELECT * FROM (SELECT '数控车床', 'CK6150', '实训车间A区-01', '高精度数控车床，用于金属零件加工', 'AVAILABLE') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM equipment WHERE name = '数控车床') LIMIT 1;

INSERT INTO equipment (name, model, location, description, status)
SELECT * FROM (SELECT '3D打印机', 'Formlabs Form 3+', '实训车间B区-05', '工业级SLA光固化3D打印机', 'AVAILABLE') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM equipment WHERE name = '3D打印机') LIMIT 1;

INSERT INTO equipment (name, model, location, description, status)
SELECT * FROM (SELECT '激光切割机', 'LP-1390', '实训车间C区-03', 'CO2激光切割机，用于板材切割与雕刻', 'AVAILABLE') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM equipment WHERE name = '激光切割机') LIMIT 1;

INSERT INTO training_video (equipment_id, title, video_url, duration, description, teacher_id)
SELECT * FROM (SELECT 1, '数控车床安全操作培训', 'https://example.com/videos/cnc-lathe-training.mp4', 1800, '完整的数控车床操作培训，包括安全注意事项、操作流程和维护保养', 1) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM training_video WHERE title = '数控车床安全操作培训') LIMIT 1;

INSERT INTO training_video (equipment_id, title, video_url, duration, description, teacher_id)
SELECT * FROM (SELECT 2, '3D打印机操作与维护', 'https://example.com/videos/3d-printer-training.mp4', 1200, '3D打印机操作指南，包括模型导入、参数设置和日常维护', 1) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM training_video WHERE title = '3D打印机操作与维护') LIMIT 1;

INSERT INTO quiz_question (video_id, question_type, segment_name, time_point, question_text, option_a, option_b, option_c, option_d, correct_answer, explanation)
SELECT * FROM (SELECT 1, 'DANGER_STEP', '危险步骤-开机前检查', 120, '数控车床开机前，以下哪项是必须检查的？', '刀具是否锋利', '安全门是否完好', '润滑油液位', '控制面板是否清洁', 'B', '安全门是保障操作人员安全的关键设备，开机前必须确认安全门完好且能正常锁闭。') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM quiz_question WHERE video_id = 1 AND time_point = 120) LIMIT 1;

INSERT INTO quiz_question (video_id, question_type, segment_name, time_point, question_text, option_a, option_b, option_c, option_d, correct_answer, explanation)
SELECT * FROM (SELECT 1, 'DANGER_STEP', '危险步骤-主轴旋转', 300, '主轴旋转时，以下哪种行为是绝对禁止的？', '观察加工情况', '测量工件尺寸', '调整冷却液流量', '打开安全门', 'D', '主轴旋转时打开安全门可能导致严重人身伤害，这是最危险的操作之一。') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM quiz_question WHERE video_id = 1 AND time_point = 300) LIMIT 1;

INSERT INTO quiz_question (video_id, question_type, segment_name, time_point, question_text, option_a, option_b, option_c, option_d, correct_answer, explanation)
SELECT * FROM (SELECT 1, 'CLEANING_REQUIREMENT', '清洁要求-日常清洁', 600, '数控车床加工完成后，清理切屑应该使用什么工具？', '用手直接清理', '用压缩空气吹', '用专用钩子或毛刷', '用冷却液冲洗', 'C', '用手直接清理可能被锋利切屑割伤，压缩空气可能导致切屑飞溅伤人，应使用专用工具。') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM quiz_question WHERE video_id = 1 AND time_point = 600) LIMIT 1;

INSERT INTO quiz_question (video_id, question_type, segment_name, time_point, question_text, option_a, option_b, option_c, option_d, correct_answer, explanation)
SELECT * FROM (SELECT 1, 'SHUTDOWN_SEQUENCE', '关机顺序-停机步骤', 900, '数控车床正确的关机顺序是？', '直接关闭总电源', '停主轴→回原点→关系统→关总电源', '停主轴→关总电源', '回原点→停主轴→关系统', 'B', '正确的关机顺序可以保护设备，避免数据丢失和机械损伤。') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM quiz_question WHERE video_id = 1 AND time_point = 900) LIMIT 1;

INSERT INTO quiz_question (video_id, question_type, segment_name, time_point, question_text, option_a, option_b, option_c, option_d, correct_answer, explanation)
SELECT * FROM (SELECT 2, 'DANGER_STEP', '危险步骤-树脂处理', 180, '处理光敏树脂时，必须佩戴什么防护装备？', '安全帽', '防紫外线手套和护目镜', '防毒面具', '隔热手套', 'B', '光敏树脂对皮肤和眼睛有刺激性，紫外线也会损伤眼睛，必须佩戴专用防护装备。') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM quiz_question WHERE video_id = 2 AND time_point = 180) LIMIT 1;

INSERT INTO quiz_question (video_id, question_type, segment_name, time_point, question_text, option_a, option_b, option_c, option_d, correct_answer, explanation)
SELECT * FROM (SELECT 2, 'CLEANING_REQUIREMENT', '清洁要求-树脂槽清洁', 480, '清洁3D打印机树脂槽时，以下哪种做法是正确的？', '使用金属刮刀清理', '将剩余树脂直接倒入下水道', '使用专用塑料铲和异丙醇清洁', '用大量清水冲洗', 'C', '金属刮刀会刮伤树脂槽，树脂不能直接排放，应用专用工具和溶剂清洁。') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM quiz_question WHERE video_id = 2 AND time_point = 480) LIMIT 1;

INSERT INTO quiz_question (video_id, question_type, segment_name, time_point, question_text, option_a, option_b, option_c, option_d, correct_answer, explanation)
SELECT * FROM (SELECT 2, 'SHUTDOWN_SEQUENCE', '关机顺序-UV后处理', 720, '打印完成后，模型后处理的正确顺序是？', '直接取下使用', '酒精清洗→UV固化→去除支撑', '去除支撑→酒精清洗→UV固化', 'UV固化→酒精清洗→去除支撑', 'C', '先去除支撑便于清洁，酒精清洗去除表面树脂，最后UV固化确保完全硬化。') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM quiz_question WHERE video_id = 2 AND time_point = 720) LIMIT 1;

INSERT INTO training_video (equipment_id, title, video_url, duration, description, teacher_id)
SELECT * FROM (SELECT 3, '激光切割机安全操作培训', 'https://example.com/videos/laser-cutter-training.mp4', 1500, '激光切割机操作培训，涵盖安全防护、参数设置与清洁维护', 1) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM training_video WHERE title = '激光切割机安全操作培训') LIMIT 1;

INSERT INTO quiz_question (video_id, question_type, segment_name, time_point, question_text, option_a, option_b, option_c, option_d, correct_answer, explanation)
SELECT * FROM (SELECT 3, 'DANGER_STEP', '危险步骤-激光防护', 90, '操作激光切割机时必须佩戴什么防护装备？', '普通眼镜', '专用激光防护眼镜', '墨镜', '无需佩戴', 'B', '激光会对眼睛造成永久性损伤，必须佩戴对应波长的专用激光防护眼镜。') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM quiz_question WHERE video_id = 3 AND time_point = 90) LIMIT 1;

INSERT INTO quiz_question (video_id, question_type, segment_name, time_point, question_text, option_a, option_b, option_c, option_d, correct_answer, explanation)
SELECT * FROM (SELECT 3, 'DANGER_STEP', '危险步骤-材料选择', 240, '以下哪种材料严禁在激光切割机上加工？', '亚克力板', '木板', 'PVC材料', '皮革', 'C', 'PVC材料激光切割会产生有毒的氯气，严重危害健康并损坏设备。') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM quiz_question WHERE video_id = 3 AND time_point = 240) LIMIT 1;

INSERT INTO quiz_question (video_id, question_type, segment_name, time_point, question_text, option_a, option_b, option_c, option_d, correct_answer, explanation)
SELECT * FROM (SELECT 3, 'CLEANING_REQUIREMENT', '清洁要求-透镜清洁', 540, '清洁激光切割机聚焦透镜时，正确的操作是？', '用普通纸巾擦拭', '用棉签蘸酒精擦拭', '用专用镜头纸和光学清洁剂', '直接用嘴吹', 'C', '普通纸巾和棉签会刮伤透镜涂层，必须使用专业光学清洁用品。') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM quiz_question WHERE video_id = 3 AND time_point = 540) LIMIT 1;

INSERT INTO quiz_question (video_id, question_type, segment_name, time_point, question_text, option_a, option_b, option_c, option_d, correct_answer, explanation)
SELECT * FROM (SELECT 3, 'SHUTDOWN_SEQUENCE', '关机顺序-排烟系统', 900, '激光切割机关机的正确顺序是？', '直接切断总电源', '关闭激光→关闭排烟→关闭电脑→关总电源', '关闭排烟→关闭激光→关总电源', '关闭电脑→关总电源', 'B', '正确关机顺序确保设备安全，排烟系统应最后关闭以排净有害气体。') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM quiz_question WHERE video_id = 3 AND time_point = 900) LIMIT 1;
