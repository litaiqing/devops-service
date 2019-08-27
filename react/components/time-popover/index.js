import React, { Fragment, memo } from 'react';
import PropTypes from 'prop-types';
import { Tooltip } from 'choerodon-ui';
import TimeAgo from 'timeago-react';
import { formatDate } from '../../utils';

function TimePopover({ datetime, placement }) {
  let time = datetime;

  if (time) {
    if (typeof datetime === 'string') {
      time = Math.min(Date.now(), new Date(datetime.replace(/-/g, '/')).getTime());
    }
  } else {
    time = null;
  }

  return <Fragment>
    {time ? <Tooltip
      placement={placement}
      title={formatDate(time)}
    >
      <TimeAgo
        datetime={time}
        locale={Choerodon.getMessage('zh_CN', 'en')}
      />
    </Tooltip> : null}
  </Fragment>;
}

TimePopover.propTypes = {
  placement: PropTypes.string,
};

TimePopover.defaultProps = {
  placement: 'top',
};

export default memo(TimePopover);
