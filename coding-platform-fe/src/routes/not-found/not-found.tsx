import { Button, Result } from 'antd';
import { useNavigate } from 'react-router';

const NotFound: React.FC = () => {
  let navigate = useNavigate();
  return (
    <Result
      status="404"
      title="404"
      subTitle="Sorry, the page you visited does not exist."
      extra={
        <Button
          type="primary"
          onClick={() => navigate(-1)}>
          返回
        </Button>
      }
    />
  );
};

export default NotFound;
