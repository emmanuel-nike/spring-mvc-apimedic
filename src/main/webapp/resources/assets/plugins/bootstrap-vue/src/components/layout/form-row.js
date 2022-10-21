import Vue, { mergeData } from '../../vue'
import { NAME_FORM_ROW } from '../../constants/components'

export const props = {
  tag: {
    type: String,
    default: 'div'
  }
}

// @vue/component
export const BFormRow = /*#__PURE__*/ Vue.extend({
  name: NAME_FORM_ROW,
  functional: true,
  props,
  render(h, { props, data, children }) {
    return h(
      props.tag,
      mergeData(data, {
        staticClass: 'form-row'
      }),
      children
    )
  }
})
